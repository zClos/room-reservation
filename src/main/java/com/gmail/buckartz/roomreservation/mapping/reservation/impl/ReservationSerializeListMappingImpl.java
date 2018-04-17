package com.gmail.buckartz.roomreservation.mapping.reservation.impl;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.mapping.reservation.ReservationSerializeListMapping;
import com.gmail.buckartz.roomreservation.mapping.reservation.mapper.ReservationSerializeMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ReservationSerializeListMappingImpl implements ReservationSerializeListMapping {
    @Override
    public List<ReservationSerializeMapper> toJson(List<Reservation> from) {
        List<ReservationSerializeMapper> toJsonMapperList = new LinkedList<>();
        from.forEach(reservation -> {
            toJsonMapperList.add(ReservationSerializeMapper.builder()
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
