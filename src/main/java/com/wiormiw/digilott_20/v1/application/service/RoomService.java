package com.wiormiw.digilott_20.v1.application.service;

import com.wiormiw.digilott_20.v1.domain.dto.RoomRequestDTO;
import com.wiormiw.digilott_20.v1.domain.dto.RoomResponseDTO;
import com.wiormiw.digilott_20.v1.domain.models.Role;
import com.wiormiw.digilott_20.v1.domain.models.Room;
import com.wiormiw.digilott_20.v1.domain.models.User;
import com.wiormiw.digilott_20.v1.infrastructure.repository.RoomRepository;
import com.wiormiw.digilott_20.v1.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public RoomService(
            RoomRepository roomRepository,
            UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public RoomResponseDTO createRoom(RoomRequestDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Room room = new Room(UUID.randomUUID(), dto.name(), dto.description(),
                Set.of(user), Room.RoomStatus.PENDING,
                dto.minParticipants(), dto.maxParticipants(), dto.participationCost());

        roomRepository.save(room);
        return new RoomResponseDTO(
                room.getId(),
                room.getName(),
                room.getDescription(),
                room.getStatus(),
                room.getMinParticipants(),
                room.getMaxParticipants(),
                room.getParticipationCost());
    }

    public Set<RoomResponseDTO> getUserRooms() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user.getRooms().stream()
                .map(room -> new RoomResponseDTO(
                        room.getId(),
                        room.getName(),
                        room.getDescription(),
                        room.getStatus(),
                        room.getMinParticipants(),
                        room.getMaxParticipants(),
                        room.getParticipationCost()))
                .collect(Collectors.toSet());
    }

    public List<RoomResponseDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(room -> new RoomResponseDTO(
                        room.getId(),
                        room.getName(),
                        room.getDescription(),
                        room.getStatus(),
                        room.getMinParticipants(),
                        room.getMaxParticipants(),
                        room.getParticipationCost()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteRoom(UUID roomId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        if (!room.getParticipants().contains(user) && user.getRoles().stream().noneMatch(role -> role.getName() == Role.RoleType.ADMIN)) {
            throw new IllegalArgumentException("You are not authorized to delete this room");
        }

        roomRepository.deleteById(roomId);
    }

}
