package com.example.staySphereProject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.Date;

    public class BookingsDTO {

    @NotNull(message = "User ID req")
    private String userId;

    @NotNull(message = "Listing ID req")
    private String listingId;

    /**
    @NotEmpty(message = "Booking name req")
    private String bookingName;
     **/

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
    private boolean pending;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getListingId() { return listingId; }
    public void setListingId(String listingId) { this.listingId = listingId; }

        /**
    public String getBookingName() { return bookingName; }
    public void setBookingName(String bookingName) { this.bookingName = bookingName; }
         **/

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public boolean isPending() { return pending; }
    public void setPending(boolean pending) { this.pending = pending; }


}
