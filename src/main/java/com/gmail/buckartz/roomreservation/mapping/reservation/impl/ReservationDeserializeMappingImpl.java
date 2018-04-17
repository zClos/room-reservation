package com.gmail.buckartz.roomreservation.mapping.reservation.impl;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.mapping.reservation.ReservationDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.reservation.mapper.ReservationDeserializeMapper;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeService;
import com.gmail.buckartz.roomreservation.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReservationDeserializeMappingImpl implements ReservationDeserializeMapping {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoomService roomService;

    @Override
    public Reservation toObject(ReservationDeserializeMapper from) {
        return Reservation.builder()
                .employee(employeeService.findOne(from.getEmployeeId()))
                .room(roomService.findOne(Optional.of(from.getRoomId())
                        .orElseThrow(() -> new NullPointerException("Can't be null"))))
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse(from.getReservedFrom())))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse(from.getReservedTo())))
                .reason(from.getReason())
                .build();
    }
}
