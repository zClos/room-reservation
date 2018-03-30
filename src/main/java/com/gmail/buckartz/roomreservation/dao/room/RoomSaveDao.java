package com.gmail.buckartz.roomreservation.dao.room;

import com.gmail.buckartz.roomreservation.domain.Room;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomSaveDao extends org.springframework.data.repository.Repository<Room, Long> {
    void save(Room room);
}
