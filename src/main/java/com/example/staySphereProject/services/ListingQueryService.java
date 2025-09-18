package com.example.staySphereProject.services;

import com.example.staySphereProject.converters.ListingDTOConverter;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ListingQueryService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final ListingDTOConverter listingDTOConverter;

    public ListingQueryService(ListingRepository listingRepository, UserRepository userRepository, ListingDTOConverter listingDTOConverter) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
        this.listingDTOConverter = listingDTOConverter;
    }
}
