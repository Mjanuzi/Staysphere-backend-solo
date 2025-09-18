package com.example.staySphereProject.services;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class CostCalculationService {

    //Calculating bookingcost. extracting logic from bookingservice
    public double calculateBookingCost(LocalDate startDate, LocalDate endDate, double pricePerNight) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        if (pricePerNight < 0) {
            throw new IllegalArgumentException("Price per night cannot be negative");
        }

        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        return numberOfDays * pricePerNight;
    }


}
