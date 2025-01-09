package ru.mirea.GAliev.pkmn.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.GAliev.pkmn.entities.CardEntity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card implements Serializable {

    protected UUID id;
    protected PokemonStage pokemonStage;
    protected String name;
    protected int hp;
    protected EnergyType pokemonType;
    protected Card evolvesFrom;
    protected List<AttackSkill> skills;
    protected EnergyType weaknessType;
    protected EnergyType resistanceType;
    protected String retreatCost;
    protected String gameSet;
    protected char regulationMark;
    protected Student pokemonOwner;
    protected String number;

    public Character getRegulationMark() {
        return regulationMark;
    }
    public void setRegulationMark(Character value) {
        regulationMark = value;
    }



    public static Card fromEntity(CardEntity entity) {
        Card.CardBuilder card =  Card.builder()
                .id(entity.getId())
                .pokemonStage(PokemonStage.valueOf(entity.getStage()))
                .name(entity.getName())
                .hp(entity.getHp())
                .skills(entity.getAttackSkills())
                .retreatCost(entity.getRetreatCost())
                .gameSet(entity.getGameSet())
                .regulationMark(entity.getRegulationMark())
                .number(entity.getCardNumber());

        if(entity.getPokemonType() != null) {
            card.pokemonType(EnergyType.valueOf(entity.getPokemonType()));
        } else card.pokemonType(null);
        if(entity.getEvolvesFrom() != null) {
            card.evolvesFrom(Card.fromEntity(entity.getEvolvesFrom()));
        } else card.evolvesFrom(null);
        if(entity.getWeaknessType() != null) {
            card.weaknessType(EnergyType.valueOf(entity.getWeaknessType()));
        } else card.weaknessType(null);
        if(entity.getResistanceType() != null && !entity.getResistanceType().equals("null")) {
            card.resistanceType(EnergyType.valueOf(entity.getResistanceType()));
        } else card.resistanceType(null);
        if(entity.getPokemonOwner() != null) {
            card.pokemonOwner(Student.fromEntity(entity.getPokemonOwner()));
        } else card.pokemonOwner(null);
        return card.build();
    }
}
