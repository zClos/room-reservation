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
public class RoomGetServiceTests {
    @Autowired
    private RoomSaveService roomSaveService;

    @Autowired
    private RoomGetByNumberService getByNumberService;

    @Autowired
    private RoomGetByIdService roomGetByIdService;

    @Autowired
    private RoomGetAllService roomGetAllService;

    @Test
    public void getRoomByNumber() {
        Room room = Room.builder()
                .number("123a")
                .sitsCount(22)
                .build();
        roomSaveService.save(room);

        assertNotNull(getByNumberService.findByNumber(room.getNumber()));
    }

    @Test
    public void getRoomById() {
        Room room = Room.builder()
                .number("123b")
                .sitsCount(6)
                .build();
        roomSaveService.save(room);

        assertEquals(room, roomGetByIdService.findById(room.getId()));
    }

    @Test
    public void getAllRoom() {
        Room room1 = Room.builder()
                .number("123a")
                .sitsCount(22)
                .build();
        Room room2 = Room.builder()
                .number("123b")
                .sitsCount(21)
                .build();
        Room room3 = Room.builder()
                .number("123c")
                .sitsCount(20)
                .build();
        roomSaveService.save(room1);
        roomSaveService.save(room2);
        roomSaveService.save(room3);

        assertEquals(3, roomGetAllService.findAll().size());
    }
}
