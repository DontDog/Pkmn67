package ru.mirea.GAliev.pkmn.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.GAliev.pkmn.entities.StudentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
    Optional<StudentEntity> findByFirstNameAndFamilyNameAndSurName(String firstName, String familyName, String surName);

    List<StudentEntity> findAllByGroup(String group);
}
