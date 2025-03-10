package com.example.staySphereProject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @NotNull(message = "Booking date req")
    private LocalDateTime bookingDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @NotNull(message = "Start date req")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @NotNull(message = "End date req")
    private Date endDate;

    private boolean status;
    private boolean pending;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getListingId() { return listingId; }
    public void setListingId(String listingId) { this.listingId = listingId; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public boolean isPending() { return pending; }
    public void setPending(boolean pending) { this.pending = pending; }


}
