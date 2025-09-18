package com.example.staySphereProject.services;


import com.example.staySphereProject.exeptions.ConflictException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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












}
