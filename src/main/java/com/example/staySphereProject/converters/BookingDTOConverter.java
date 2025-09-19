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
import java.time.ZoneId;
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

    //Extracted UTC logic from creatBooking and made a method that is reuseable. Adjusting start and end date, timezone conversation.
    public LocalDate[] convertToLocalDates(BookingsDTO dto) {
        if (dto == null || dto.getStartDate() == null || dto.getEndDate() == null) {
            throw new IllegalArgumentException("DTO and dates cannot be null");
        }

        LocalDate startDate = dto.getStartDate().toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDate().plusDays(1);

        LocalDate endDate = dto.getEndDate().toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDate().plusDays(1);

        return new LocalDate[]{startDate, endDate};
    }

    // Updated validation of existing booking compared to our last updateBooking
    public Bookings applyUpdate(Bookings existing, BookingsDTO dto,
                                List<LocalDate> newBookedDates, double newTotalCost) {
        if (dto.getBookingDate() != null) {
            existing.setBookingDate(dto.getBookingDate());
        }
        if (dto.getStartDate() != null) {
            existing.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            existing.setEndDate(dto.getEndDate());
        }
        if (newBookedDates != null) {
            existing.setBookedDates(newBookedDates);
        }

        existing.setTotalCost(newTotalCost);
        existing.setStatus(dto.isStatus());
        existing.setPending(dto.isPending());

        return existing;
    }
}
