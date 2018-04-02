package com.gmail.buckartz.roomreservation.controller.room;

import com.gmail.buckartz.roomreservation.controller.DefaultHeaderValues;
import com.gmail.buckartz.roomreservation.mapping.room.RoomMapping;
import com.gmail.buckartz.roomreservation.mapping.room.mapper.RoomMapper;
import com.gmail.buckartz.roomreservation.service.room.RoomSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/room")
@DefaultHeaderValues
public class RoomController {
    @Autowired
    private RoomSaveService roomSaveService;

    @Autowired
    private RoomMapping roomMapping;

    @PostMapping
    public ResponseEntity saveRoom(@Valid @RequestBody RoomMapper mapper) {
        roomSaveService.save(roomMapping.toObject(mapper));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
