package com.example.staySphereProject.converters;

import com.example.staySphereProject.dto.BookingsDTO;
import com.example.staySphereProject.dto.BookingsResponse;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Bookings;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.Residence;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class BookingDTOConverter {

    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    public BookingDTOConverter(UserRepository userRepository, ListingRepository listingRepository) {
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    public Bookings fromDTO(BookingsDTO dto, List<LocalDate> bookedDates, double totalCost) {
        Bookings booking = new Bookings();

        booking.setUserId(dto.getUserId());
        booking.setListingId(dto.getListingId());
        booking.setBookingDate(dto.getBookingDate());
        booking.setStartDate(dto.getStartDate());
        booking.setEndDate(dto.getEndDate());
        booking.setBookedDates(bookedDates);
        booking.setTotalCost(totalCost);
        booking.setStatus(dto.isStatus());
        booking.setPending(dto.isPending());

        return booking;
    }

    public BookingsResponse toResponse(Bookings booking) {
        BookingsResponse response = new BookingsResponse();

        response.setBookingID(booking.getBookingID());
        response.setUserId(booking.getUserId());
        response.setListingId(booking.getListingId());
        response.setBookingDate(booking.getBookingDate());
        response.setStartDate(booking.getStartDate());
        response.setEndDate(booking.getEndDate());
        response.setTotalCost(booking.getTotalCost());
        response.setStatus(booking.isStatus());
        response.setPending(booking.isPending());

        User user = userRepository.findById(booking.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Listing listing = listingRepository.findById(booking.getListingId())
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        response.setBookingName(user.getUsername() + ", we would like to wish you a pleasant stay at " + listing.getListingTitle() + "!");

        return response;
    }

}
