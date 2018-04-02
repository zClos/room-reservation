package com.gmail.buckartz.roomreservation.mapping.room.mapper;

import com.gmail.buckartz.roomreservation.validation.room.RoomUniqueNumber;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

@Getter
@Setter
@GroupSequence({RoomMapper.class, RoomMapper.RoomUniqueNumberGroup.class})
public class RoomMapper {
    @NotEmpty(message = "{NotEmpty.roomMapper.number}")
    @Size(max = 6, message = "{Size.roomMapper.number}")
    @RoomUniqueNumber(groups = RoomUniqueNumberGroup.class)
    private String number;
    private Integer sitsCount;

    public interface RoomUniqueNumberGroup {
    }

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
