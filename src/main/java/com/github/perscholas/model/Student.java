package com.github.perscholas.model;

// TODO - Annotate and Implement respective interface and define behaviors
public class Student implements StudentInterface {
    private String email;
    private String name;
    private String password;

    public Student(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public Student() {}

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
