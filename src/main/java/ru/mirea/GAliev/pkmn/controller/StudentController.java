package ru.mirea.GAliev.pkmn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mirea.GAliev.pkmn.services.StudentService;
import ru.mirea.GAliev.pkmn.models.Student;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("")
    public Student getStudentByFullName(@RequestBody Student student) {
        return studentService.getByFullName(student.getFirstName(), student.getSurName(), student.getFamilyName());
    }

    @PostMapping("")
    public Student addStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/all")
    public List<Student> getAllStudent() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{group}")
    public List<Student> getAllStudentByGroup(@PathVariable String group) {
        return studentService.getStudentsByGroup(group);
    }
}
