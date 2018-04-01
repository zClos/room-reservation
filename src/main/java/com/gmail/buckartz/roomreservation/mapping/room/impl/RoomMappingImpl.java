package com.gmail.buckartz.roomreservation.mapping.room.impl;

import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.mapping.room.RoomMapping;
import com.gmail.buckartz.roomreservation.mapping.room.mapper.RoomMapper;
import org.springframework.stereotype.Service;

@Service
public class RoomMappingImpl implements RoomMapping {
    @Override
    public Room toObject(RoomMapper from) {
        return Room.builder()
                .number(from.getNumber())
                .sitsCount(from.getSitsCount())
                .build();
    }
}
