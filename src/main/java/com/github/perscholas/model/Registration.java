package com.github.perscholas.model;

public class Registration implements RegistrationInterface {
    private Integer id;
    private String email;

    public Registration(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
