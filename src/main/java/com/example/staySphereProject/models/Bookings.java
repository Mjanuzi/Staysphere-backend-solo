package com.example.staySphereProject.models;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "bookings")
public class Bookings {

    @Id
    private String bookingID;

    @DBRef
    @NotBlank
    private User bookingUser;

    @DBRef
    @NotBlank
    private Listing bookingListing;

    @NotEmpty
    private String bookingName;

    @NotNull
    private LocalDateTime bookingDate;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
    @NotNull (message = "Cost must be a number")
    @PositiveOrZero(message = "Cost can't be negative")
    private Double totalCost;
    @NotNull
    private Boolean status;
    @NotNull
    private Boolean isPending;






}
