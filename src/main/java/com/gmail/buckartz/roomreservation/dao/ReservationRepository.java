package com.gmail.buckartz.roomreservation.dao;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long>, PagingAndSortingRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {
    @Query(value = "SELECT count(*) FROM reservation " +
            "WHERE ROOM_ID = :id" +
            " AND ((RESERVED_FROM <= :reservedFrom AND RESERVED_TO >= :reservedTo)" +
            " OR ((RESERVED_FROM BETWEEN :reservedFrom AND :reservedTo)" +
            " OR (RESERVED_TO BETWEEN :reservedFrom AND :reservedTo))) " +
            " LIMIT 1", nativeQuery = true)
    int isReservedRoomForPeriod(@Param("id") Long id,
                                @Param("reservedFrom") Timestamp reservedFrom,
                                @Param("reservedTo") Timestamp reservedTo);

}
