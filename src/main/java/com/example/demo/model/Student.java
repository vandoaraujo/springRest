package com.example.demo.model;

import javax.persistence.Entity;

@Entity
public class Student extends AbstractEntity{

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
