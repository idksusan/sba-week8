package com.github.perscholas.model;

public class Registration implements RegistrationInterface {
    private Integer id;

    public Registration(Integer id) {
        this.id = id;
    }
    @Override
    public Integer getId() {
        return id;
    }
}
