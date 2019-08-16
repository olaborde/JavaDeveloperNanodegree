package com.laborde.springbootvalidation.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class User {
    @NotBlank(message = "Username cannot be empty")
    private String name;
    @NotBlank(message = "password cannot be empty")
    @Length(min = 0, max = 100, message="length of password should between 6 to 10 characters")
    private String password;
    @Min(value = 0)
    @Max(value = 100)
    private Double gpa;
    @Email
    private String email;

    public User(@NotBlank(message = "Username cannot be empty") String name, @NotBlank(message = "password cannot be empty") @Length(min = 0, max = 100, message = "length of password should between 6 to 10 characters") String password, @Min(value = 0) @Max(value = 100) Double gpa, @Email String email) {
        this.name = name;
        this.password = password;
        this.gpa = gpa;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
}
