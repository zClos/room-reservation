package com.gmail.buckartz.roomreservation.service.room;

import com.gmail.buckartz.roomreservation.config.UnitTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Room;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@UnitTestConfiguration
@RunWith(SpringRunner.class)
public class RoomServicesTests {
    @Autowired
    private RoomSaveService roomSaveService;

    @Autowired
    private RoomGetByNumberService getByNumberService;

    @Autowired
    private RoomGetByIdService roomGetByIdService;

    @Test
    public void saveRoom() {
        Room room = Room.builder()
                .number("123a")
                .sitsCount(24)
                .build();
        roomSaveService.save(room);
        assertNotNull(room.getId());
    }

    @Test
    public void RoomGetByNumber() {
        Room room = Room.builder()
                .number("123a")
                .sitsCount(22)
                .build();
        roomSaveService.save(room);

        assertNotNull(getByNumberService.getByNumber(room.getNumber()));
    }

    @Test
    public void getRoomById() {
        Room room = Room.builder()
                .number("123b")
                .sitsCount(6)
                .build();
        roomSaveService.save(room);

        assertEquals(room, roomGetByIdService.getById(room.getId()));
    }
}
