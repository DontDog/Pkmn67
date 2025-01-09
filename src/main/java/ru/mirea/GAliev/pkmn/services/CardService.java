package ru.mirea.GAliev.pkmn.services;

import ru.mirea.GAliev.pkmn.models.Card;

import java.util.List;

public interface CardService {
    public List<Card> getAllCards();
    public Card getCardByName(String name);
    public Card getCardByOwnerData(String firstName, String surName, String familyName);
    public Card getCardById(String id);
    public Card createCard(Card card);
    public String getImageUrl(String name);
}
