package com.example.staySphereProject.dto;

import com.example.staySphereProject.models.Role;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class RegisterRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @NotBlank
    private String country;


    private Integer age;

    @NotNull
    private boolean isActive;

    private Set<Role> roles;

    public RegisterRequest(String username, String password, String email, String country, Integer age, boolean isActive, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.country = country;
        this.age = age;
        this.isActive = isActive;
        this.roles = roles;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }

    public  Integer getAge() {
        return age;
    }

    public void setAge( Integer age) {
        this.age = age;
    }

    public @NotBlank String getCountry() {
        return country;
    }

    public void setCountry(@NotBlank String country) {
        this.country = country;
    }

    @NotNull
    public boolean isActive() {
        return isActive;
    }

    public void setActive(@NotNull boolean active) {
        isActive = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


}
