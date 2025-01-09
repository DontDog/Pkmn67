package ru.mirea.GAliev.pkmn.daos;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.mirea.GAliev.pkmn.entities.StudentEntity;
import ru.mirea.GAliev.pkmn.repositorie.StudentRepository;
import ru.mirea.GAliev.pkmn.models.Student;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentDAO {
    private final StudentRepository studentRepository;

    @SneakyThrows
    public List<Student> getAllStudents() {
        return studentRepository.findAll().stream().map(Student::fromEntity).toList();
    }

    @SneakyThrows
    public Student getStudentByFullName(String firstName, String surName, String familyName) {
        return Student.fromEntity(studentRepository.findByFirstNameAndFamilyNameAndSurName(firstName, familyName, surName)
                .orElseThrow(() -> new IllegalArgumentException("Студент по имени: " + firstName + " " + surName + " " + familyName + ", не найден")));
    }

    @SneakyThrows
    public List<Student> getStudentsByGroup(String group) {
        return studentRepository.findAllByGroup(group)
                .stream().map(Student::fromEntity).toList();
    }

    @SneakyThrows
    public Student createStudent(Student student) {
        return Student.fromEntity(studentRepository.save(StudentEntity.toEntity(student)));
    }
}
