package com.example.staySphereProject.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.Date;

public class BookingsResponse {
    private String bookingID;
    private String userId;
    private String username;
    private String listingId;
    private String bookingName;
    private LocalDateTime bookingDate;
    private Date startDate;
    private Date endDate;
    private double totalCost;
    private boolean status;
    private boolean pending;


    public String getBookingID() { return bookingID; }
    public void setBookingID( String bookingID) { this.bookingID = bookingID; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getListingId() { return listingId; }
    public void setListingId(String listingId) { this.listingId = listingId; }

    public String getBookingName() { return bookingName; }
    public void setBookingName(String bookingName) { this.bookingName = bookingName; }

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
    public void setPending(boolean pending) { pending = pending; }

}
