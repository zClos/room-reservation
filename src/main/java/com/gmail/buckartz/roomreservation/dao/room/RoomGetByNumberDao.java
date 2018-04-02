package com.gmail.buckartz.roomreservation.dao.room;

import com.gmail.buckartz.roomreservation.domain.Room;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomGetByNumberDao extends org.springframework.data.repository.Repository<Room, String> {
    Room getByNumber(String number);
}
