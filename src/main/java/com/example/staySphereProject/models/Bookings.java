package com.example.staySphereProject.models;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document(collection = "bookings")
public class Bookings {

    @Id
    private String bookingID;

    @NotNull
    private String userId;

    @NotNull
    private String listingId;


    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getListingId() { return listingId; }
    public void setListingId(String listingId) { this.listingId = listingId; }


    @NotEmpty
    private String bookingName;

    @NotNull
    private LocalDateTime bookingDate;

    @NotNull
    private List<LocalDate> bookedDates;

    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull (message = "Cost must be a number")
    @PositiveOrZero(message = "Cost can't be negative")
    private double totalCost;
    private boolean status;
    private boolean isPending;


    public Bookings() {
    }

    public @NotEmpty String getBookingID() {
        return bookingID;
    }

    public void setBookingID(@NotEmpty String bookingID) {
        this.bookingID = bookingID;
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

    public List<LocalDate> getBookedDates() { return bookedDates;
    }

    public void setBookedDates(List<LocalDate> bookedDates) { this.bookedDates = bookedDates;
    }
}
