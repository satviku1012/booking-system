package com.example.booking_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String roomNumber;
    
    @Column(nullable = false)
    private String roomType; // SINGLE, DOUBLE, SUITE
    
    @Column(nullable = false)
    private Double pricePerNight;
    
    @Column(nullable = false)
    private Integer capacity;
    
    private String description;
}

