package com.example.booking_system.service;

import com.example.booking_system.entity.Room;
import com.example.booking_system.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

/**
 * Service responsible for room-related operations, including creating rooms and
 * retrieving rooms by ID/type and by availability for a given date range.
 */
@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }
    
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Room not found"));
    }
    
    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        return roomRepository.findAvailableRooms(checkIn, checkOut);
    }
    
    public List<Room> getRoomsByType(String roomType) {
        return roomRepository.findByRoomType(roomType);
    }
}

