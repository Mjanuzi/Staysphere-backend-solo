package com.example.staySphereProject.builder;

import com.example.staySphereProject.converters.BookingDTOConverter;
import com.example.staySphereProject.dto.BookingsDTO;
import com.example.staySphereProject.exeptions.ConflictException;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.UserRepository;
import com.example.staySphereProject.services.AvailabilityService;
import com.example.staySphereProject.services.CostCalculationService;
import com.example.staySphereProject.services.DateRangeService;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class BookingBuilder {

    //Immutable dependdencies
    private final BookingDTOConverter converter;
    private final CostCalculationService costCalculator;
    private final DateRangeService dateRangeService;
    private final AvailabilityService availabilityService;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final CheckAuthentication checkAuthentication;

    //Mutable
    private BookingsDTO bookingDTO;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<LocalDate> requestedDates;
    private User user;
    private Listing listing;
    private double totalCost;
    private boolean validated;


    public BookingBuilder(BookingDTOConverter converter,
                          CostCalculationService costCalculator,
                          DateRangeService dateRangeService,
                          AvailabilityService availabilityService,
                          UserRepository userRepository,
                          ListingRepository listingRepository,
                          CheckAuthentication checkAuthentication) {
        this.converter = converter;
        this.costCalculator = costCalculator;
        this.dateRangeService = dateRangeService;
        this.availabilityService = availabilityService;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
        this.checkAuthentication = checkAuthentication;
    }

    //Set booking details from DTO and converts dates
    public BookingBuilder withBookingDetails(BookingsDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("BookingsDTO cannot be null");
        }

        this.bookingDTO = dto;

        // Convert dates using the converter to maintain consistency
        LocalDate[] dates = converter.convertToLocalDates(dto);
        this.startDate = dates[0];
        this.endDate = dates[1];

        // Generate requested dates using the DateRangeService
        this.requestedDates = dateRangeService.generateDateRange(startDate, endDate);

        return this;
    }

    // Validate and get the user making the booking
    public BookingBuilder withUserValidation() {
        if (bookingDTO == null) {
            throw new IllegalStateException("Must call withBookingDetails() first");
        }

        // Validate authentication
        checkAuthentication.validateAuthenticatedUser(bookingDTO.getUserId());

        // Retrieve and validate user exists
        this.user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return this;
    }


    // Validate and retrieve the listing we want to book
    public BookingBuilder withListingValidation() {
        if (bookingDTO == null) {
            throw new IllegalStateException("Must call withBookingDetails() first");
        }

        // Retrieev and validate if listing exists
        this.listing = listingRepository.findById(bookingDTO.getListingId())
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        return this;
    }

    //Availability validation
    public BookingBuilder withAvailabilityValidation() {
        if (listing == null || requestedDates == null) {
            throw new IllegalStateException("Must call withListingValidation() and withBookingDetails() first");
        }

        // Check availability using the AvailabilityService
        boolean isAvailable = availabilityService.isDateRangeAvailable(
                listing.getListingId(), startDate, endDate);

        if (!isAvailable) {
            throw new ConflictException("Requested dates are not available");
        }

        this.validated = true;
        return this;
    }

    //Caulculate total cost for booking
    public BookingBuilder withCostCalculation() {
        if (listing == null || startDate == null || endDate == null) {
            throw new IllegalStateException("Must call withListingValidation() and withBookingDetails() first");
        }

        this.totalCost = costCalculator.calculateBookingCost(
                startDate, endDate, listing.getListingPricePerNight());

        return this;
    }





}
