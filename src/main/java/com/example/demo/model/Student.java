package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

public class Student {

    private int id;
    private String name;
    public static List<Student> studentList;

    static{
        studentRepository();
    }

    public Student(int id, String name) {
        this(name);
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    private static void studentRepository(){
        studentList = new ArrayList<>(asList(new Student(1,"vando"), new Student(2, "flavia")));
    }
}
