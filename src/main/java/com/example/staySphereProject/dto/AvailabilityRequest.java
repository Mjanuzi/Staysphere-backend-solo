package com.example.staySphereProject.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AvailabilityRequest {
    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    public @NotNull LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull LocalDate startDate) {
        this.startDate = startDate;
    }

    public @NotNull LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull LocalDate endDate) {
        this.endDate = endDate;
    }
}
