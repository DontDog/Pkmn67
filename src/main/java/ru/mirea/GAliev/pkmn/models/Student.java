package ru.mirea.GAliev.pkmn.models;

import lombok.*;
import ru.mirea.GAliev.pkmn.entities.StudentEntity;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    private UUID id;
    private String firstName;
    private String surName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String value) {
        firstName = value;
    }


    public String getSurName() {
        return surName;
    }
    public void setSurName(String value) {
        surName = value;
    }

    private String familyName;
    public String getFamilyName() {
        return familyName;
    }
    public void setFamilyName(String value) {
        familyName = value;
    }

    @Getter
    @Setter
    private String group;

    public static Student fromEntity(StudentEntity entity) {
        return Student.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .surName(entity.getSurName())
                .familyName(entity.getFamilyName())
                .group(entity.getGroup())
                .build();
    }

    @Override
    public String toString() {
        return surName + " " + firstName + " " + familyName + " " + group;
    }
}
