package com.gmail.buckartz.roomreservation.controller.room;

import com.gmail.buckartz.roomreservation.controller.DefaultHeaderValues;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.mapping.content_page.PageSerializeMapping;
import com.gmail.buckartz.roomreservation.mapping.content_page.mapper.PageSerializeMapper;
import com.gmail.buckartz.roomreservation.mapping.room.RoomDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.room.mapper.RoomDeserializeMapper;
import com.gmail.buckartz.roomreservation.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/room")
@DefaultHeaderValues
public class RoomController {
    @Autowired
    private RoomDeserializeMapping roomMapping;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PageSerializeMapping<Room> pageSerializeMapping;

    @PostMapping
    public ResponseEntity saveRoom(@Valid @RequestBody RoomDeserializeMapper mapper) {
        roomService.save(roomMapping.toObject(mapper));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageSerializeMapper> findAllRooms(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                                            @RequestParam(value = "page", required = false) Optional<Integer> size) {
        Page<Room> rooms = roomService.findAll(page.filter(value -> value > 0).map(value -> value - 1).orElse(0),
                size.filter(value -> value > 0).orElse(15));
        return (rooms.getContent().isEmpty()) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(pageSerializeMapping.toJson(rooms), HttpStatus.OK);
    }
}
