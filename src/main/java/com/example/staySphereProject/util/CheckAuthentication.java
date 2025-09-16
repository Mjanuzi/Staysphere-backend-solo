package com.example.staySphereProject.util;

import com.example.staySphereProject.dto.AuthResponse;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.Residence;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.UserRepository;
import com.example.staySphereProject.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class CheckAuthentication {

    private final UserRepository userRepository;

    public CheckAuthentication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // Check if user is authenticated
    public User validateAuthenticatedUser(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        //  Hämta userDetails och hitta användaren i databasen
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User loggedInUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Logged-in user not found"));

        //  Kontrollera att userId matchar den inloggade användaren
        if (!loggedInUser.getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to perform this action for another user!");
        }

        return loggedInUser;
    }

    //Validate that the authenticated user owns the given listing
    public void validateListingOwned(Listing listing){
        if (listing instanceof Residence){
            Residence residence = (Residence) listing;
            validateAuthenticatedUser(residence.getHost().getId());
        }
    }


}
