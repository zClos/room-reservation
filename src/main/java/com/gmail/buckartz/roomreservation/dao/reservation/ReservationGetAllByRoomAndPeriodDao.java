package com.gmail.buckartz.roomreservation.dao.reservation;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface ReservationGetAllByRoomAndPeriodDao extends org.springframework.data.repository.Repository<Reservation, Long> {
    @Query("SELECT count(res) FROM Reservation res " +
            "WHERE (res.reservedFrom <= :reservedFrom AND res.reservedTo >= :reservedTo)" +
            " OR ((res.reservedFrom BETWEEN :reservedFrom AND :reservedTo)" +
            " OR (res.reservedTo BETWEEN :reservedFrom AND :reservedTo))" +
            " AND res.room.id = :id")
    int findAllByRoomIdAndPeriod(@Param("id") Long id, @Param("reservedFrom") Timestamp reservedFrom, @Param("reservedTo") Timestamp reservedTo);
}
