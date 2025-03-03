package com.example.staySphereProject.services;

import com.example.staySphereProject.dto.BookingsDTO;
import com.example.staySphereProject.dto.BookingsResponse;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Bookings;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.BookingsRepository;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingsService {
    private final BookingsRepository bookingsRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public BookingsService(BookingsRepository bookingsRepository, ListingRepository listingRepository, UserRepository userRepository) {
        this.bookingsRepository = bookingsRepository;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    public BookingsResponse getBookingById(String bookingId) {
        Bookings booking = bookingsRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return convertToDTO(booking);
    }

    public BookingsResponse updateBooking(String bookingId, BookingsDTO bookingsDTO) {
        Bookings existingBooking = bookingsRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        existingBooking.setBookingDate(bookingsDTO.getBookingDate());
        existingBooking.setStartDate(bookingsDTO.getStartDate());
        existingBooking.setEndDate(bookingsDTO.getEndDate());
        //existingBooking.setTotalCost(bookingsDTO.getTotalCost());
        existingBooking.setStatus(bookingsDTO.isStatus());
        existingBooking.setPending(bookingsDTO.isPending());

        Listing listing = listingRepository.findById(existingBooking.getListingId())
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        long days = calculateNumberOfDays(existingBooking.getStartDate(), existingBooking.getEndDate());

        double totalCost = days * listing.getListingPricePerNight();
        existingBooking.setTotalCost(totalCost);

        Bookings updatedBooking = bookingsRepository.save(existingBooking);
        return convertToDTO(updatedBooking);
    }

    private long calculateNumberOfDays(Date startDate, Date endDate) {
        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (end.isBefore(start) || end.isEqual(start)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        return ChronoUnit.DAYS.between(start, end);
    }

    public void deleteBooking(String bookingId) {
        bookingsRepository.deleteById(bookingId);
    }


    public List<BookingsResponse> getAllBookings() {
        List<Bookings> bookings = bookingsRepository.findAll();

        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<BookingsResponse> getUserBookings(String userId) {

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }

        List<Bookings> bookings = bookingsRepository.findByUserId(userId);

        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    // Convert Bookings entity to BookingsResponse DTO

    private BookingsResponse convertToDTO(Bookings booking) {
        BookingsResponse response = new BookingsResponse();
        response.setBookingID(booking.getBookingID());
        response.setUserId(booking.getUserId());

        User user = userRepository.findById(booking.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        response.setListingId(booking.getListingId());
        Listing listing = listingRepository.findById(booking.getListingId())
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        response.setBookingName("For " + user.getUsername() + " at " + listing.getListingTitle());
        response.setBookingDate(booking.getBookingDate());
        response.setStartDate(booking.getStartDate());
        response.setEndDate(booking.getEndDate());
        response.setTotalCost(booking.getTotalCost());
        response.setStatus(booking.isStatus());
        response.setPending(booking.isPending());

        return response;
    }


    public BookingsResponse createBooking(BookingsDTO bookingsDTO) {

        if (!userRepository.existsById(bookingsDTO.getUserId())) {
            throw new ResourceNotFoundException("User not found");
        }
        if (!listingRepository.existsById(bookingsDTO.getListingId())) {
            throw new ResourceNotFoundException("Listing not found");
        }

        Listing listing = listingRepository.findById(bookingsDTO.getListingId())
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        long days = calculateNumberOfDays(bookingsDTO.getStartDate(), bookingsDTO.getEndDate());

        double totalCost = days * listing.getListingPricePerNight();

        Bookings booking = new Bookings();
        booking.setUserId(bookingsDTO.getUserId()); // Use DTO getter
        booking.setListingId(bookingsDTO.getListingId());
        booking.setBookingDate(bookingsDTO.getBookingDate());
        booking.setStartDate(bookingsDTO.getStartDate());
        booking.setEndDate(bookingsDTO.getEndDate());
        booking.setTotalCost(totalCost);
        booking.setStatus(bookingsDTO.isStatus());
        booking.setPending(bookingsDTO.isPending());

        // Save and convert to response
        Bookings savedBooking = bookingsRepository.save(booking);
        return convertToDTO(savedBooking);
    }
}
