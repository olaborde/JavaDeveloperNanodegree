package com.laborde.model;

import javax.validation.constraints.NotBlank;

public class User {
    @NotBlank(message = "Username cannot be empty")
    private String name;
    @NotBlank(message = "password cannot be empty")
    private String password;
    private Double gpa;

    public User(@NotBlank(message = "Username cannot be empty") String name, @NotBlank(message = "password cannot be empty") String password, Double gpa) {
        this.name = name;
        this.password = password;
        this.gpa = gpa;
    }
}
