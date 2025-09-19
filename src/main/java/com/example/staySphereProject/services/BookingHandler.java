package com.example.staySphereProject.services;


import com.example.staySphereProject.builder.BookingBuilder;
import com.example.staySphereProject.dto.BookingsDTO;
import com.example.staySphereProject.models.Bookings;
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

    //Standard booking creater for now
    public Bookings createStandardBooking(BookingsDTO dto) {
        return builder.reset()
                .withBookingDetails(dto)
                .withUserValidation()
                .withListingValidation()
                .withAvailabilityValidation()
                .withCostCalculation()
                .build();
    }

    //Method that get results midway in the process, which is good when we get results in the workflow
    public Object[] getBookingComponents(BookingsDTO dto) {
        builder.reset()
                .withBookingDetails(dto)
                .withUserValidation()
                .withListingValidation()
                .withAvailabilityValidation()
                .withCostCalculation();

        return new Object[]{
                builder.getUser(),
                builder.getListing(),
                builder.getTotalCost(),
                builder.getRequestedDates()
        };
    }

    // Pre validates a booking before it's built. We can use this as a confirm before building
    public boolean validateBookingConstruction(BookingsDTO dto) {
        try {
            builder.reset()
                    .withBookingDetails(dto)
                    .withUserValidation()
                    .withListingValidation()
                    .withAvailabilityValidation()
                    .withCostCalculation();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
