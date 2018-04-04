package com.gmail.buckartz.roomreservation.dao.reservation;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationSaveDao extends org.springframework.data.repository.Repository<Reservation, Long> {
    void save(Reservation reservation);
}
