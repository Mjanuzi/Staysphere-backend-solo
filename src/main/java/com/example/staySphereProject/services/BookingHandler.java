package com.example.staySphereProject.services;


import com.example.staySphereProject.builder.BookingBuilder;
import com.example.staySphereProject.repository.ListingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingHandler {

    private final BookingBuilder builder;
    private final ListingRepository listingRepository;

    public BookingHandler(BookingBuilder builder, ListingRepository listingRepository) {
        this.builder = builder;
        this.listingRepository = listingRepository;
    }
}
