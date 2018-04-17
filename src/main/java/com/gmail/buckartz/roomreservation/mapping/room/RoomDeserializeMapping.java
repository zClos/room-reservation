package com.gmail.buckartz.roomreservation.mapping.room;

import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.mapping.DeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.room.mapper.RoomDeserializeMapper;

public interface RoomDeserializeMapping extends DeserializeMapping<Room, RoomDeserializeMapper> {
}
