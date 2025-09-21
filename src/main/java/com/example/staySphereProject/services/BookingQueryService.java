package com.example.staySphereProject.services;


import com.example.staySphereProject.converters.BookingDTOConverter;
import com.example.staySphereProject.repository.BookingsRepository;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.ResidenceRepository;
import com.example.staySphereProject.repository.UserRepository;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookingQueryService {

    // Immutable dependencies
    private final BookingsRepository bookingsRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final ResidenceRepository residenceRepository;
    private final BookingDTOConverter converter;
    private final CheckAuthentication checkAuthentication;


    public BookingQueryService(BookingsRepository bookingsRepository,
                               UserRepository userRepository,
                               ListingRepository listingRepository,
                               ResidenceRepository residenceRepository,
                               BookingDTOConverter converter,
                               CheckAuthentication checkAuthentication) {
        this.bookingsRepository = bookingsRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
        this.residenceRepository = residenceRepository;
        this.converter = converter;
        this.checkAuthentication = checkAuthentication;
    }
}
