package com.example.staySphereProject.builder;

import com.example.staySphereProject.converters.BookingDTOConverter;
import com.example.staySphereProject.dto.BookingsDTO;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.services.AvailabilityService;
import com.example.staySphereProject.services.CostCalculationService;
import com.example.staySphereProject.services.DateRangeService;
import com.example.staySphereProject.services.UserService;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class BookingBuilder {

    //Immutable dependdencies
    private final BookingDTOConverter converter;
    private final CostCalculationService costCalculationService;
    private final DateRangeService dateRangeService;
    private final AvailabilityService availabilityService;
    private final UserService userService;
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
                          CostCalculationService costCalculationService,
                          DateRangeService dateRangeService,
                          AvailabilityService availabilityService,
                          UserService userService,
                          ListingRepository listingRepository,
                          CheckAuthentication checkAuthentication) {
        this.converter = converter;
        this.costCalculationService = costCalculationService;
        this.dateRangeService = dateRangeService;
        this.availabilityService = availabilityService;
        this.userService = userService;
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


}
