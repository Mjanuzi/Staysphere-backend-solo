package com.example.staySphereProject.dto;

import java.time.LocalDate;

public class AvailabilityResponse {
    private LocalDate startDate;
    private LocalDate endDate;

    public AvailabilityResponse() {
    }

    public AvailabilityResponse(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
} 