package com.gmail.buckartz.roomreservation.mapping.reservation.impl;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.mapping.reservation.ReservationMapping;
import com.gmail.buckartz.roomreservation.mapping.reservation.mapper.ReservationMapper;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeGetByIdService;
import com.gmail.buckartz.roomreservation.service.room.RoomGetByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReservationMappingImpl implements ReservationMapping {

    @Autowired
    private EmployeeGetByIdService employeeGetByIdServiceImpl;

    @Autowired
    private RoomGetByIdService roomGetByIdService;

    @Override
    public Reservation toObject(ReservationMapper from) {
        return Reservation.builder()
                .employee(employeeGetByIdServiceImpl.getById(Optional.of(from.getEmployeeId())
                        .orElseThrow(() -> new NullPointerException("Can't be null"))))
                .room(roomGetByIdService.getById(Optional.of(from.getRoomId())
                        .orElseThrow(() -> new NullPointerException("Can't be null"))))
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse(from.getReservedFrom())))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse(from.getReservedTo())))
                .reason(from.getReason())
                .build();
    }
}
