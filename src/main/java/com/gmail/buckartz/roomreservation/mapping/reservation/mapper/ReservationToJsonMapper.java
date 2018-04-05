package com.gmail.buckartz.roomreservation.mapping.reservation.mapper;

import com.gmail.buckartz.roomreservation.domain.Employee;
import com.gmail.buckartz.roomreservation.domain.Room;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationToJsonMapper {
    private Long id;
    private Room room;
    private Employee employee;
    private String reservedFrom;
    private String reservedTo;
    private String reason;

    public static ReservationToJsonMapperBuilder builder() {
        return new ReservationToJsonMapperBuilder();
    }

    public static class ReservationToJsonMapperBuilder {
        private ReservationToJsonMapper mapper = new ReservationToJsonMapper();

        private ReservationToJsonMapperBuilder() {
        }

        public ReservationToJsonMapperBuilder id(Long id) {
            mapper.setId(id);
            return this;
        }

        public ReservationToJsonMapperBuilder room(Room room) {
            mapper.setRoom(room);
            return this;
        }

        public ReservationToJsonMapperBuilder employee(Employee employee) {
            mapper.setEmployee(employee);
            return this;
        }

        public ReservationToJsonMapperBuilder reservedFrom(String reservedFrom) {
            mapper.setReservedFrom(reservedFrom);
            return this;
        }

        public ReservationToJsonMapperBuilder reservedTo(String reservedTo) {
            mapper.setReservedTo(reservedTo);
            return this;
        }

        public ReservationToJsonMapperBuilder reason(String reason) {
            mapper.setReason(reason);
            return this;
        }

        public ReservationToJsonMapper build() {
            return mapper;
        }
    }
}
