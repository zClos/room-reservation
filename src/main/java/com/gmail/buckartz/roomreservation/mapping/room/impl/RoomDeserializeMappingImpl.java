package com.gmail.buckartz.roomreservation.mapping.room.impl;

import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.mapping.room.RoomDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.room.mapper.RoomDeserializeMapper;
import org.springframework.stereotype.Service;

@Service
public class RoomDeserializeMappingImpl implements RoomDeserializeMapping {
    @Override
    public Room toObject(RoomDeserializeMapper from) {
        return Room.builder()
                .number(from.getNumber())
                .sitsCount(from.getSitsCount())
                .build();
    }
}
