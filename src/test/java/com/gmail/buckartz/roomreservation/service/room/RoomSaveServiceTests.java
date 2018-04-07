package com.gmail.buckartz.roomreservation.service.room;

import com.gmail.buckartz.roomreservation.config.UnitTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Room;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@UnitTestConfiguration
@RunWith(SpringRunner.class)
public class RoomSaveServiceTests {
    @Autowired
    private RoomSaveService roomSaveService;

    @Test
    public void saveRoom() {
        Room room = Room.builder()
                .number("123a")
                .sitsCount(24)
                .build();
        roomSaveService.save(room);
        assertNotNull(room.getId());
    }
}
