package com.gmail.buckartz.roomreservation.service.reservation;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.service.reservation.filter.ReservationSearchFilters;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationService {
    void save(Reservation reservation);

    boolean isReservedRoomForPeriod(Long id, Timestamp from, Timestamp to);

    List<Reservation> findAllByFilter(ReservationSearchFilters searchFilters);
}
