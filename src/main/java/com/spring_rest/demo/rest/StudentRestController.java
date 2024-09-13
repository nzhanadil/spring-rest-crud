package com.spring_rest.demo.rest;

import com.spring_rest.demo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> students;

    // define @PostConstruct to load the student data ... only once in the beginning.
    @PostConstruct
    public void loadData() {
        students = new ArrayList<>();

        students.add(new Student("Mike", "Patel"));
        students.add(new Student("Mario", "Rossi"));
        students.add(new Student("Marry", "Smith"));
    }

    // define endpoint for /students
    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    // define endpoint for /students/{studentId}
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {

        // check if the student id exists in list
        if(studentId >= students.size() || studentId < 0) {
            throw new StudentNonFoundException("Student id not found - " + studentId);
        }

        return students.get(studentId);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNonFoundException exc) {

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc) {

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
