package ru.mirea.GAliev.pkmn.services;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.mirea.GAliev.pkmn.daos.CardDAO;
import ru.mirea.GAliev.pkmn.models.Card;

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

            card_ = cardDAO.createCard(card);
        }
        return card_;
    }

    public String getImageUrl(String name) {
        Card card = getCardByName(name);
        RestClient restClient = RestClient.create();
        JsonNode json = restClient.get().uri("https://api.pokemontcg.io/v2/cards?q=name:" + name + " number:" + card.getCardNumber()).retrieve().body(JsonNode.class);

        assert json != null;
        return String.valueOf(json.findValue("data").elements().next().findValue("images").findValue("large")).replace('"', ' ').trim();
    }
}
