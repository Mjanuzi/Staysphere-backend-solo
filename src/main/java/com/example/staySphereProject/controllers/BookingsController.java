package com.example.staySphereProject.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingsController {
    private final BookingsService bookingsService;

    public BookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking newBooking = bookingsService.createBooking(bookingDTO);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingsResponse>> getAllBookings() {
        List<BookingsResponse> bookings = bookingsService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingsRespone>> getUserBookings(@PathVariable String userId) {
        List<BookingsResponse> bookings = bookingsService.getUserBookings(userId);
        return ResponseEntity.ok(bookings);
    }
}
