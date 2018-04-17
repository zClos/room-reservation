package com.gmail.buckartz.roomreservation.service.room;

import com.gmail.buckartz.roomreservation.config.UnitTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Room;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@UnitTestConfiguration
@RunWith(SpringRunner.class)
public class RoomGetServiceTests {
    @Autowired
    private RoomService roomService;

    @Test
    public void getRoomByNumber() {
        Room room = Room.builder()
                .number("123a")
                .sitsCount(22)
                .build();
        roomService.save(room);

        assertNotNull(roomService.findByNumber(room.getNumber()));
    }

    @Test
    public void getRoomById() {
        Room room = Room.builder()
                .number("123b")
                .sitsCount(6)
                .build();
        roomService.save(room);

        assertTrue(roomService.exists(room.getId()));
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
        roomService.save(room1);
        roomService.save(room2);
        roomService.save(room3);

        assertEquals(3, roomService.findAll(0, 15).getContent().size());
    }
}
