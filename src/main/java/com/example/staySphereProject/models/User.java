package com.example.staySphereProject.models;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotEmpty(message = "Username cannot be empty")
    private String username;


    @Indexed(unique = true)
    @Email(message = "Please use vaild email format")
    private String email;


    //När listings är klar lägg till detta
   // private List<Listiings >OwnedListings = new ArrayList<>();


    @NotBlank
    private String country;
    //private String gender;
    //private String phoneNmr;

    @NotNull(message = "Age can not be null")
    @PositiveOrZero(message = "Age can not be negative")
    @Min(value = 1, message = "Age must be at least 1")
    private Integer age;

    @NotNull(message = "isActive can not be null")
    private boolean isActive;


    /*@Pattern(
               regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,})" +
                       ".*$",
               message = "Password must be at least 8 characters long and contain at least " +
                       "one uppercase letter, one number, and one special character"
       )*/

    private String password;
    
    private Set<Role> roles;


    public User(String id, String username, String country, String email,
                String gender, String phoneNmr, Integer age, boolean isActive,
                String password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.country = country;
        this.email = email;
       // this.gender = gender;
        //this.phoneNmr = phoneNmr;
        this.age = age;
        this.isActive = isActive;

        this.password = password;
        this.roles = roles;
    }


    public User() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotEmpty(message = "Username cannot be empty") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Username cannot be empty") String username) {
        this.username = username;
    }

    public @Email(message = "Please use vaild email format") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Please use vaild email format") String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

   /* public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNmr() {
        return phoneNmr;
    }

    public void setPhoneNmr(String phoneNmr) {
        this.phoneNmr = phoneNmr;
    }
*/
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }







}
