package ru.mirea.GAliev.pkmn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import ru.mirea.GAliev.pkmn.models.Student;

import java.util.UUID;

@Entity
@Table(name = "students")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "\"first_name\"")
    private String firstName;

    @Column(name = "\"sur_name\"")
    private String surName;

    @Column(name = "\"family_name\"")
    private String familyName;

    @Column(name = "\"group\"")
    private String group;

    @OneToOne(mappedBy = "pokemonOwner")
    private CardEntity card;

    public static StudentEntity toEntity(Student student) {
        return StudentEntity.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .surName(student.getSurName())
                .familyName(student.getFamilyName())
                .group(student.getGroup())
                .build();
    }
}
