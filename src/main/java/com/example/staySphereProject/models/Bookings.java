package com.example.staySphereProject.models;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "bookings")
public class Bookings {

    @Id
    private String bookingID;

    @DBRef
    @NotNull(message = "Booking can't have null")
    @NotBlank(message = "Need to connect user to booking")
    private User bookingUser;

    @DBRef
    @NotNull(message = "Booking can't have null")
    @NotBlank(message = "Need to connect listing to booking")
    private Listing bookingListing;

    @NotEmpty
    private String bookingName;

    @NotNull
    private LocalDateTime bookingDate;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull (message = "Cost must be a number")
    @PositiveOrZero(message = "Cost can't be negative")
    private double totalCost;
    private boolean status;
    private boolean isPending;


    public Bookings(String bookingID, User bookingUser, Listing bookingListing, String bookingName, LocalDateTime bookingDate, Date startDate, Date endDate, double totalCost, boolean status, boolean isPending) {
        this.bookingID = bookingID;
        this.bookingUser = bookingUser;
        this.bookingListing = bookingListing;
        this.bookingName = bookingName;
        this.bookingDate = bookingDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.status = status;
        this.isPending = isPending;
    }

    public Bookings() {
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public @NotNull(message = "Booking can't have null") @NotBlank(message = "Need to connect user to booking") User getBookingUser() {
        return bookingUser;
    }

    public void setBookingUser(@NotNull(message = "Booking can't have null") @NotBlank(message = "Need to connect user to booking") User bookingUser) {
        this.bookingUser = bookingUser;
    }

    public Listing getBookingListing() {
        return bookingListing;
    }

    public void setBookingListing(Listing bookingListing) {
        this.bookingListing = bookingListing;
    }

    public @NotEmpty String getBookingName() {
        return bookingName;
    }

    public void setBookingName(@NotEmpty String bookingName) {
        this.bookingName = bookingName;
    }

    public @NotNull LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(@NotNull LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public @NotNull Date getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull Date startDate) {
        this.startDate = startDate;
    }

    public @NotNull Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull Date endDate) {
        this.endDate = endDate;
    }

    @NotNull(message = "Cost must be a number")
    @PositiveOrZero(message = "Cost can't be negative")
    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(@NotNull(message = "Cost must be a number") @PositiveOrZero(message = "Cost can't be negative") double totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }
}
