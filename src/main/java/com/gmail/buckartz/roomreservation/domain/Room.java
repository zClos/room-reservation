package com.gmail.buckartz.roomreservation.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "room", schema = "public")
@Getter
public class Room {
    @Id
    @SequenceGenerator(name = "room_seq", sequenceName = "room_seq", schema = "public")
    @GeneratedValue(generator = "room_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "sits_count")
    private Integer sitsCount;

    public static RoomBuilder builder() {
        return new RoomBuilder();
    }

    public static RoomBuilder builder(Room room) {
        return new RoomBuilder();
    }

    public static class RoomBuilder {
        private Room room = new Room();

        private RoomBuilder() {
        }

        private RoomBuilder(Room room) {
            this.room = room;
        }

        public RoomBuilder number(String number) {
            room.number = number;
            return this;
        }

        public RoomBuilder sitsCount(Integer sitsCount) {
            room.sitsCount = sitsCount;
            return this;
        }

        public Room build() {
            return room;
        }
    }
}
