package com.example.staySphereProject.services;


import com.example.staySphereProject.dto.AvailabilityResponse;
import com.example.staySphereProject.exeptions.ConflictException;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Listing;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DateRangeService {


    //Validates that the date range is correct and within busniess rules
    public void validateDateRange(LocalDate startDate, LocalDate endDate){
        if(startDate == null || endDate == null){
            throw new IllegalArgumentException("startDate and endDate cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }

        if (ChronoUnit.DAYS.between(startDate, endDate) >= 90) {
            throw new IllegalArgumentException("Date range cannot exceed 90 days");
        }
    }

    //Generates a list of dates between start and end date
    public List<LocalDate> generateDateRange(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);

        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return dates;
    }

    public List<AvailabilityResponse> convertToAvailabilityRanges(List<LocalDate> availableDates) {
        if (availableDates == null || availableDates.isEmpty()) {
            return new ArrayList<>();
        }

        // Sort the dates in ascending order
        List<LocalDate> sortedDates = new ArrayList<>(availableDates);
        Collections.sort(sortedDates);

        // Convert individual dates to date ranges
        List<AvailabilityResponse> dateRanges = new ArrayList<>();
        LocalDate rangeStart = sortedDates.get(0);
        LocalDate rangeEnd = rangeStart;

        for (int i = 1; i < sortedDates.size(); i++) {
            LocalDate currentDate = sortedDates.get(i);

            // If the current date is one day after the previous end date, extend the range
            if (currentDate.isEqual(rangeEnd.plusDays(1))) {
                rangeEnd = currentDate;
            } else {
                // This date is not consecutive, so close the current range and start a new one
                dateRanges.add(new AvailabilityResponse(rangeStart, rangeEnd));
                rangeStart = currentDate;
                rangeEnd = currentDate;
            }
        }

        // Add the last range
        dateRanges.add(new AvailabilityResponse(rangeStart, rangeEnd));

        return dateRanges;
    }

    // Check if a specific date overlaps with any dates in a list
    public boolean hasDateOverlap(LocalDate startDate, LocalDate endDate, List<LocalDate> existingDates) {
        if (existingDates == null || existingDates.isEmpty()) {
            return false;
        }

        List<LocalDate> rangeToCheck = generateDateRange(startDate, endDate);
        return existingDates.stream().anyMatch(rangeToCheck::contains);
    }


}
