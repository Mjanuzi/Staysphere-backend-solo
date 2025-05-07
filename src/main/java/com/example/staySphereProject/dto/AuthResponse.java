package com.example.staySphereProject.dto;

import com.example.staySphereProject.models.Role;

import java.util.Set;

public class AuthResponse {
    private String message;
    private String username;
    private String id;
    private Set<Role> roles;
    private String jwtToken;

    // Constructor with ID and JWT token
    public AuthResponse(String message, String username, String id, Set<Role> roles, String jwtToken) {
        this.message = message;
        this.username = username;
        this.id = id;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }
    
    // Constructor with ID but without JWT token for backward compatibility
    public AuthResponse(String message, String username, String id, Set<Role> roles) {
        this.message = message;
        this.username = username;
        this.id = id;
        this.roles = roles;
        this.jwtToken = null;
    }
    
    // Constructor without ID for backward compatibility
    public AuthResponse(String message, String username, Set<Role> roles) {
        this.message = message;
        this.username = username;
        this.roles = roles;
        this.id = null;
        this.jwtToken = null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    // Getter and setter for JWT token
    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
