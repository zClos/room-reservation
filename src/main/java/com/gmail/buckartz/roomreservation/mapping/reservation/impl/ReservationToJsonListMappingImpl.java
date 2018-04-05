package com.gmail.buckartz.roomreservation.mapping.reservation.impl;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.mapping.reservation.ReservationToJsonListMapping;
import com.gmail.buckartz.roomreservation.mapping.reservation.mapper.ReservationToJsonMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ReservationToJsonListMappingImpl implements ReservationToJsonListMapping {
    @Override
    public List<ReservationToJsonMapper> toJson(List<Reservation> from) {
        List<ReservationToJsonMapper> toJsonMapperList = new LinkedList<>();
        from.forEach(reservation -> {
            toJsonMapperList.add(ReservationToJsonMapper.builder()
                    .id(reservation.getId())
                    .room(reservation.getRoom())
                    .employee(reservation.getEmployee())
                    .reservedFrom(reservation.getReservedFrom().toLocalDateTime().toString())
                    .reservedTo(reservation.getReservedTo().toLocalDateTime().toString())
                    .reason(reservation.getReason())
                    .build());
        });
        return toJsonMapperList;
    }
}
