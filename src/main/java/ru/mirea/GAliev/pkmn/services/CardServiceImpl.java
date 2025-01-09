package ru.mirea.GAliev.pkmn.services;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import ru.mirea.GAliev.pkmn.daos.CardDAO;
import ru.mirea.GAliev.pkmn.models.Card;
import ru.mirea.GAliev.pkmn.models.Student;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardDAO cardDAO;
    private final StudentService studentService;

    public List<Card> getAllCards() {
        return cardDAO.getAllCards();
    }

    public Card getCardByName(String name) {
        return cardDAO.getCardByName(name);
    }

    public Card getCardByOwnerData(String firstName, String surName, String familyName) {
        return cardDAO.getCardByOwnerFullName(firstName, surName, familyName);
    }

    public Card getCardById(String id) {
        return cardDAO.getCardById(UUID.fromString(id));
    }

    public Card createCard(Card card) {
        Card card_ = null;
        try {
            card_ = getCardByName(card.getName());
        } catch(IllegalArgumentException e) {

            Student student;
            if((student = card.getPokemonOwner()) != null) {
                try {
                    student = studentService.getByFullName(student.getFirstName(),
                            student.getSurName(), student.getFamilyName());
                } catch (IllegalArgumentException e1) {
                    student.setId(null);
                }
                card.setPokemonOwner(student);
            }

            Card evolves;
            if((evolves = card.getEvolvesFrom()) != null) {
                try {
                    evolves = getCardByName(evolves.getName());
                } catch(IllegalArgumentException e1) {
                    evolves.setId(null);
                }
                card.setEvolvesFrom(evolves);
            } else {
                card.setEvolvesFrom(null);
            }

            card_ = cardDAO.createCard(card);
        }
        return card_;
    }

    public String getImageUrl(String name) {
        Card card = getCardByName(name);
        RestClient restClient = RestClient.create();
        JsonNode json = restClient.get().uri("https://api.pokemontcg.io/v2/cards?q=name:" + name + " number:" + card.getNumber()).retrieve().body(JsonNode.class);

        assert json != null;
        return String.valueOf(json.findValue("data").elements().next().findValue("images").findValue("large")).replace('"', ' ').trim();
    }

    public byte[] getImageBytes(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, byte[].class);
    }
}
