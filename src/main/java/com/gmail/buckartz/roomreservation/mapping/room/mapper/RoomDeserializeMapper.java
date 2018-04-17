package com.gmail.buckartz.roomreservation.mapping.room.mapper;

import com.gmail.buckartz.roomreservation.validation.room.RoomUniqueNumber;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

@Getter
@Setter
@GroupSequence({RoomDeserializeMapper.class, RoomDeserializeMapper.RoomUniqueNumberGroup.class})
public class RoomDeserializeMapper {
    @NotEmpty(message = "{NotEmpty.roomDeserializeMapper.number}")
    @Size(max = 6, message = "{Size.roomDeserializeMapper.number}")
    @RoomUniqueNumber(groups = RoomUniqueNumberGroup.class)
    private String number;
    private Integer sitsCount;

    public interface RoomUniqueNumberGroup {
    }

    public static RoomMapperBuilder builder() {
        return new RoomMapperBuilder();
    }

    public static class RoomMapperBuilder {
        private RoomDeserializeMapper roomDeserializeMapper = new RoomDeserializeMapper();

        private RoomMapperBuilder() {
        }

        public RoomMapperBuilder number(String number) {
            roomDeserializeMapper.number = number;
            return this;
        }

        public RoomMapperBuilder sitsCount(Integer sitsCount) {
            roomDeserializeMapper.sitsCount = sitsCount;
            return this;
        }

        public RoomDeserializeMapper build() {
            return roomDeserializeMapper;
        }
    }

}
