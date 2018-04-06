package com.gmail.buckartz.roomreservation.dao.reservation;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationGettingFilteredDao
        extends org.springframework.data.repository.Repository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {
}
