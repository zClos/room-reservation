package com.gmail.buckartz.roomreservation.controller.room;

import com.gmail.buckartz.roomreservation.controller.DefaultHeaderValues;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.mapping.room.RoomMapping;
import com.gmail.buckartz.roomreservation.mapping.room.mapper.RoomMapper;
import com.gmail.buckartz.roomreservation.service.room.RoomGetAllService;
import com.gmail.buckartz.roomreservation.service.room.RoomSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(value = "/room")
@DefaultHeaderValues
public class RoomController {
    @Autowired
    private RoomSaveService roomSaveService;


    @Autowired
    private RoomMapping roomMapping;

    @Autowired
    private RoomGetAllService getAllService;

    @PostMapping
    public ResponseEntity saveRoom(@Valid @RequestBody RoomMapper mapper) {
        roomSaveService.save(roomMapping.toObject(mapper));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<Room>> getAllRooms() {
        Set<Room> rooms = getAllService.findAll();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
}
