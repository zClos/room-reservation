package com.gmail.buckartz.roomreservation.service.room;

import com.gmail.buckartz.roomreservation.domain.Room;
import org.springframework.data.domain.Page;

public interface RoomService {
    void save(Room room);

    Room findOne(Long id);

    boolean exists(Long id);

    Room findByNumber(String number);

    Page<Room> findAll(int page, int size);
}
