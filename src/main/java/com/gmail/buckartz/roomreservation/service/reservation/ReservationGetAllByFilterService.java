package com.gmail.buckartz.roomreservation.service.reservation;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.service.GetAllByFilterService;
import com.gmail.buckartz.roomreservation.service.reservation.filter.ReservationSearchFilters;

public interface ReservationGetAllByFilterService extends GetAllByFilterService<Reservation, ReservationSearchFilters> {
}
