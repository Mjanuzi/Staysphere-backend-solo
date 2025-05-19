package com.example.staySphereProject.controllers;

import com.example.staySphereProject.models.Language;
import com.example.staySphereProject.models.Role;
import com.example.staySphereProject.models.Rule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enums")
public class Enumcontroller {

    @GetMapping("/languages")
    public List<Language> getLanguages() {
        return Arrays.asList(Language.values());
    }

    @GetMapping("/roles")
    public List<Role> getRoles() {
        return Arrays.asList(Role.values());
    }

    @GetMapping("/rules")
    public List<Rule> getRules() {
        return Arrays.asList(Rule.values());
    }

    @GetMapping("/all")
    public Map<String, List<?>> getAllEnums() {
        return Map.of(
                "languages", Arrays.asList(Language.values()),
                "roles", Arrays.asList(Role.values()),
                "rules", Arrays.asList(Rule.values())
        );
    }
}