package com.example.demo.endpoint;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("students")
public class StudentEndPoint {

    private final StudentRepository studentDao;

    @Autowired
    public StudentEndPoint(StudentRepository dao){
        this.studentDao = dao;
    }

    @GetMapping
    public ResponseEntity<?> listAll(){
       return new ResponseEntity<>(studentDao.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id)
    {
        Optional<Student> student = studentDao.findById(id);
        if(student == null)
            return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Student student){
        return new ResponseEntity<>(studentDao.save(student), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@PathVariable("id") Long id ){
        studentDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student){
        studentDao.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
