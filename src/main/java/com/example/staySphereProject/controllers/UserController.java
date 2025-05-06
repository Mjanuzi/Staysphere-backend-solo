package com.example.staySphereProject.controllers;

import com.example.staySphereProject.models.User;
import com.example.staySphereProject.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        
        // Create a response with user data
        Map<String, Object> userProfileData = new HashMap<>();
        userProfileData.put("id", user.getId());
        userProfileData.put("username", user.getUsername());
        userProfileData.put("email", user.getEmail());
        userProfileData.put("country", user.getCountry());
        userProfileData.put("age", user.getAge());
        userProfileData.put("roles", user.getRoles());
        
        return ResponseEntity.ok(userProfileData);
    }
}
