package com.example.staySphereProject.services;

import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AvailabilityService {

    private final ListingRepository listingRepository;
    private final DateRangeService dateRangeService;
    private final CheckAuthentication checkAuthentication;

    public AvailabilityService(ListingRepository listingRepository, DateRangeService dateRangeService, CheckAuthentication checkAuthentication) {
        this.listingRepository = listingRepository;
        this.dateRangeService = dateRangeService;
        this.checkAuthentication = checkAuthentication;
    }
    
}
