package com.gmail.buckartz.roomreservation.mapping.reservation;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.mapping.DeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.reservation.mapper.ReservationDeserializeMapper;

public interface ReservationDeserializeMapping extends DeserializeMapping<Reservation, ReservationDeserializeMapper> {
}
