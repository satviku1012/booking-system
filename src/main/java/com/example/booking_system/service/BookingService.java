package com.example.booking_system.service;

import com.example.booking_system.dto.BookingRequest;
import com.example.booking_system.entity.Booking;
import com.example.booking_system.entity.Room;
import com.example.booking_system.repository.BookingRepository;
import com.example.booking_system.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service responsible for booking-related business logic, including validation,
 * availability checks, price calculation, and creating/cancelling bookings.
 */
@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    
    @Transactional
    public Booking createBooking(BookingRequest request) {
        // Validate dates
        if (request.getCheckOutDate().isBefore(request.getCheckInDate()) ||
            request.getCheckOutDate().isEqual(request.getCheckInDate())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        
        // Find room
        Room room = roomRepository.findById(request.getRoomId()).orElseThrow(() -> new RuntimeException("Room not found"));
        
        // Check availability
        boolean hasConflict = bookingRepository.existsConflictingBooking(room.getId(), request.getCheckInDate(), request.getCheckOutDate());
        
        if (hasConflict) {
            throw new RuntimeException("Room is not available for selected dates");
        }
        
        // Calculate total price
        long nights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        double totalPrice = nights * room.getPricePerNight();
        
        // Create booking
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setGuestName(request.getGuestName());
        booking.setGuestEmail(request.getGuestEmail());
        booking.setGuestPhone(request.getGuestPhone());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setTotalPrice(totalPrice);
        booking.setStatus("CONFIRMED");
        
        return bookingRepository.save(booking);
    }
    
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    }
    
    public List<Booking> getBookingsByEmail(String email) {
        return bookingRepository.findByGuestEmail(email);
    }
    
    @Transactional
    public Booking cancelBooking(Long id) {
        Booking booking = getBookingById(id);
        booking.setStatus("CANCELLED");
        return bookingRepository.save(booking);
    }
}
