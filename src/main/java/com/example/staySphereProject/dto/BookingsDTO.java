package com.example.staySphereProject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.Date;
/**
    public class BookingsDTO {

    @NotNull(message = "User ID req")
    private String userId;

    @NotNull(message = "Listing ID req")
    private String listingId;

    @NotEmpty(message = "Booking name req")
    private String bookingName;

    @NotNull(message = "Booking date req")
    private LocalDateTime bookingDate;

    @NotNull(message = "Start date req")
    private Date startDate;

    @NotNull(message = "End date req")
    private Date endDate;

    @NotNull(message = "Total cost req")
    @PositiveOrZero(message = "Total cost can't be negative")
    private double totalCost;

    private boolean status;
    private boolean isPending;

}
**/