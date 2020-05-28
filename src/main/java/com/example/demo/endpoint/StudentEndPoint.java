package com.example.demo.endpoint;

import com.example.demo.error.CustomErrorType;
import com.example.demo.error.ResourceNotFoundException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
        verifyIfStudentExists(id);
        return new ResponseEntity<>(studentDao.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<?> getStudentByName(@PathVariable String name){
        List<Student> student = studentDao.findByNameIgnoreCaseContaining(name);
        if(student == null){
            return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody Student student){
        return new ResponseEntity<>(studentDao.save(student), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@PathVariable("id") Long id ){
        verifyIfStudentExists(id);
        studentDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student){
        verifyIfStudentExists(student.getId());
        studentDao.save(student);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void verifyIfStudentExists(Long id){
        Optional<Student> student = studentDao.findById(id);
        if(!student.isPresent())
            throw new ResourceNotFoundException("Student not found for ID " + id);
    }
}
