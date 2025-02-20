package com.example.staySphereProject.services;

import com.example.staySphereProject.repository.BookingsRepository;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingsService {
    private final BookingsRepository bookingsRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public BookingsService(BookingsRepository bookingsRepository, ListingRepository listingRepository, UserRepository userRepository) {
        this.bookingsRepository = bookingsRepository;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }
}
