package ru.mirea.GAliev.pkmn.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import ru.mirea.GAliev.pkmn.models.User;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String SECRET = System.getenv("JWT_SECRET");
    private static final String DEBUG_KEY = "VP5nKyAgVP5nKyAgVP5nKyAgVP5nKyAgVP5nKyAgVP5nKyAgVP5nKyAgVP5nKyAg";
    private static final long EXPIRATION_TIME = 86400000;
    private final JdbcUserDetailsManager jdbcUserDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserDetails verify(String token) {
        try {
            Claims claims;
            claims = Jwts.parserBuilder()
                    .setSigningKey(Objects.requireNonNullElse(SECRET, DEBUG_KEY).getBytes())
                    .build().parseClaimsJws(token).getBody();
            return jdbcUserDetailsManager.loadUserByUsername(claims.getSubject());
        } catch(Exception e) {
            throw new RuntimeException("Неверный JWT", e);
        }
    }

    public String createToken(String username, List<GrantedAuthority> authorities) {
        String authoritiesString = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        SecretKey key;
        if(SECRET == null) {
            key = Keys.hmacShaKeyFor(DEBUG_KEY.getBytes());

        } else {
            key = Keys.hmacShaKeyFor(SECRET.getBytes());
        }
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", authoritiesString)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public void registerUser(String username, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        jdbcUserDetailsManager.createUser(new User(username,
                hashedPassword, true, authorities));
    }
}
