package ru.mirea.GAliev.pkmn.services;

import ru.mirea.GAliev.pkmn.models.Student;

import java.util.List;

public interface StudentService {
    public Student getByFullName(String firstName, String surName, String familyName);
    public Student createStudent(Student student);
    public List<Student> getAllStudents();
    public List<Student> getStudentsByGroup(String group);
}
