package ru.mirea.GAliev.pkmn.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.GAliev.pkmn.entities.CardEntity;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {

    Optional<CardEntity> findByName(String name);
    Optional<CardEntity> findById(UUID id);

    @Query("FROM CardEntity card WHERE card.pokemonOwner.firstName = :firstName AND " +
            "card.pokemonOwner.familyName = :familyName AND " +
            "card.pokemonOwner.surName = :surName")
    Optional<CardEntity> findByOwnerFullName(String firstName, String surName,
                                             String familyName);
}
