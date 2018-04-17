package com.gmail.buckartz.roomreservation.mapping.reservation;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.mapping.SerializeMapping;
import com.gmail.buckartz.roomreservation.mapping.reservation.mapper.ReservationSerializeMapper;

import java.util.List;

public interface ReservationSerializeListMapping extends SerializeMapping<List<ReservationSerializeMapper>, List<Reservation>> {
}
