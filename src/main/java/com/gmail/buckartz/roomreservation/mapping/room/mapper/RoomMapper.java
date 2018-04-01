package com.gmail.buckartz.roomreservation.mapping.room.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomMapper {
    private String number;
    private Integer sitsCount;

    public static RoomMapperBuilder builder() {
        return new RoomMapperBuilder();
    }

    public static class RoomMapperBuilder {
        private RoomMapper roomMapper = new RoomMapper();

        private RoomMapperBuilder() {
        }

        public RoomMapperBuilder number(String number) {
            roomMapper.number = number;
            return this;
        }

        public RoomMapperBuilder sitsCount(Integer sitsCount) {
            roomMapper.sitsCount = sitsCount;
            return this;
        }

        public RoomMapper build() {
            return roomMapper;
        }
    }

}
