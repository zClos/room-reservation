package com.gmail.buckartz.roomreservation.dao.room;

import com.gmail.buckartz.roomreservation.domain.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoomGetAllDao extends org.springframework.data.repository.Repository<Room, Long> {
    @Query("SELECT r FROM Room r")
    Set<Room> findAll();
}
