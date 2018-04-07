package com.gmail.buckartz.roomreservation.dao.reservation;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationGetAllByEmployeeDao extends org.springframework.data.repository.Repository<Reservation, Long> {
    List<Reservation> findAllByEmployeeId(Long id);
}
