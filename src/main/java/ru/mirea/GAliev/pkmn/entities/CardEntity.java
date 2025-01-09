package ru.mirea.GAliev.pkmn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.mirea.GAliev.pkmn.models.AttackSkill;
import ru.mirea.GAliev.pkmn.models.Card;
import ru.mirea.GAliev.pkmn.utils.SkillDeserializer;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardEntity {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "hp", columnDefinition = "int2")
    private int hp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "evolves_from_id")
    private CardEntity evolvesFrom;

    @Column(name = "game_set")
    private String gameSet;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pokemon_owner_id")
    private StudentEntity pokemonOwner;

    @Column(name = "stage")
    private String stage;

    @Column(name = "retreat_cost")
    private String retreatCost;

    @Column(name = "weakness_type")
    private String weaknessType;

    @Column(name = "resistance_type")
    private String resistanceType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attack_skills")
    @JsonDeserialize(using = SkillDeserializer.class)
    private List<AttackSkill> attackSkills;

    @Column(name = "pokemon_type")
    private String pokemonType;

    @Column(name = "regulation_mark")
    private char regulationMark;

    @Column(name = "card_number")
    private String cardNumber;


    public static CardEntity toEntity(Card card) {
        return CardEntity.builder()
                .name(card.getName())
                .hp(card.getHp())
                .evolvesFrom(card.getEvolvesFrom() != null ? CardEntity.toEntity(card.getEvolvesFrom()) : null)
                .gameSet(card.getGameSet())
                .pokemonOwner(card.getPokemonOwner() != null ? StudentEntity.toEntity(card.getPokemonOwner()) : null)
                .stage(card.getPokemonStage() != null ? card.getPokemonStage().toString() : null)
                .retreatCost(card.getRetreatCost())
                .weaknessType(card.getPokemonStage() != null ? card.getWeaknessType().toString() : null)
                .resistanceType(card.getResistanceType() != null ? card.getResistanceType().toString() : null)
                .attackSkills(card.getSkills())
                .pokemonType(String.valueOf(card.getPokemonType()))
                .regulationMark(card.getRegulationMark())
                .cardNumber(card.getNumber())
                .build();
    }
}
