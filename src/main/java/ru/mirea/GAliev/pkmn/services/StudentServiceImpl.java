package ru.mirea.GAliev.pkmn.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.GAliev.pkmn.daos.StudentDAO;
import ru.mirea.GAliev.pkmn.models.Student;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentDAO studentDAO;

    public Student getByFullName(String firstName, String surName, String familyName) {
        return studentDAO.getStudentByFullName(firstName, surName, familyName);
    }

    public Student createStudent(Student student) {
        try {
            getByFullName(student.getFirstName(),
                    student.getSurName(),
                    student.getFamilyName());
            throw new IllegalArgumentException("Студент не найден");
        } catch (IllegalArgumentException e1) {
            return studentDAO.createStudent(student);
        }
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    public List<Student> getStudentsByGroup(String group) {
        return studentDAO.getStudentsByGroup(group);
    }
}
