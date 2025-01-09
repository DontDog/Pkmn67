package ru.mirea.GAliev.pkmn.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.mirea.GAliev.pkmn.filters.JwtAuthenticationFilter;
import ru.mirea.GAliev.pkmn.services.JwtService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JdbcUserDetailsManager jdbcUserDetailsManager;
    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                customizer ->
                        customizer
                                .requestMatchers(HttpMethod.GET, "/api/v1/cards").permitAll()
                                .requestMatchers("/api/v1/cards/{name}").permitAll()
                                .requestMatchers("/api/v1/cards/owner").permitAll()
                                .requestMatchers( HttpMethod.POST, "/api/v1/cards").hasRole("ADMIN")
                                .requestMatchers( HttpMethod.POST, "/api/v1/students").hasRole("ADMIN")
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/reg").permitAll()
                                .anyRequest().authenticated()
        );
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(form -> form.successForwardUrl("/success"));

        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(new JwtAuthenticationFilter(jwtService),
                UsernamePasswordAuthenticationFilter.class);
        http.userDetailsService(jdbcUserDetailsManager);
        return http.build();
    }
}
