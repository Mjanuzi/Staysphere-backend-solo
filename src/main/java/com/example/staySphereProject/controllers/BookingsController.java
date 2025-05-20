package com.example.staySphereProject.controllers;


import com.example.staySphereProject.dto.BookingsDTO;
import com.example.staySphereProject.dto.BookingsResponse;
import com.example.staySphereProject.models.Bookings;
import com.example.staySphereProject.services.BookingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingsController {
    private final BookingsService bookingsService;

    public BookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @PostMapping
    public ResponseEntity<BookingsResponse> createBooking(@RequestBody BookingsDTO bookingsDTO) {
        BookingsResponse newBooking = bookingsService.createBooking(bookingsDTO);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingsResponse>> getAllBookings() {
        List<BookingsResponse> bookings = bookingsService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }


    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingsResponse> getBookingById(@PathVariable String bookingId) {
        BookingsResponse booking = bookingsService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }

    // Sorting parameter handling for host/user bookings
    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<BookingsResponse>> getHostBookings(
            @PathVariable String hostId,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        // SortOrder parameter allow us to sort dynamticly with asc/desc based on start date
        List<BookingsResponse> bookings = bookingsService.getHostBookings(hostId, sortOrder);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingsResponse>> getUserBookings(
            @PathVariable String userId,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        List<BookingsResponse> bookings = bookingsService.getUserBookings(userId, sortOrder);
        return ResponseEntity.ok(bookings);
    }

    //Partial update using patch with available included for recalculation
    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingsResponse> updateBooking(@PathVariable String bookingId, @RequestBody BookingsDTO bookingsDTO) {
        BookingsResponse updatedBooking = bookingsService.updateBooking(bookingId, bookingsDTO);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/admin/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String bookingId) {
        bookingsService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listings/{listingId}/bookings")
    public ResponseEntity<List<BookingsResponse>> getBookingsByListingId(
            @PathVariable String listingId) {
        List<BookingsResponse> bookings = bookingsService.getBookingsByListingId(listingId);
        return ResponseEntity.ok(bookings);
    }
}
