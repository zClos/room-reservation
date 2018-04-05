package com.gmail.buckartz.roomreservation.mapping.reservation;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.mapping.ToJsonMapping;
import com.gmail.buckartz.roomreservation.mapping.reservation.mapper.ReservationToJsonMapper;

import java.util.List;

public interface ReservationToJsonListMapping extends ToJsonMapping<List<ReservationToJsonMapper>, List<Reservation>> {
}
