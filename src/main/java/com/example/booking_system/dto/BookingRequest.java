package com.example.booking_system.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * Since some of the fields in the Booking object shouldn't be decided by the client, Spring
 * will convert the JSON sent by the client into an instance of this class.
 */
@Data
public class BookingRequest {
    @NotNull(message = "Room ID is required")
    private Long roomId;
    
    @NotBlank(message = "Guest name is required")
    private String guestName;
    
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String guestEmail;
    
    @NotBlank(message = "Phone number is required")
    private String guestPhone;
    
    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date must be today or in the future")
    private LocalDate checkInDate;
    
    @NotNull(message = "Check-out date is required")
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;
}
