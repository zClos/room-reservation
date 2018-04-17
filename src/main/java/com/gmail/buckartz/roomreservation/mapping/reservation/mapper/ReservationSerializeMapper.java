package com.gmail.buckartz.roomreservation.mapping.reservation.mapper;

import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.domain.employee.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationSerializeMapper {
    private Long id;
    private Room room;
    private Employee employee;
    private String reservedFrom;
    private String reservedTo;
    private String reason;

    public static ReservationSerializeMapperBuilder builder() {
        return new ReservationSerializeMapperBuilder();
    }

    public static class ReservationSerializeMapperBuilder {
        private ReservationSerializeMapper mapper = new ReservationSerializeMapper();

        private ReservationSerializeMapperBuilder() {
        }

        public ReservationSerializeMapperBuilder id(Long id) {
            mapper.setId(id);
            return this;
        }

        public ReservationSerializeMapperBuilder room(Room room) {
            mapper.setRoom(room);
            return this;
        }

        public ReservationSerializeMapperBuilder employee(Employee employee) {
            mapper.setEmployee(employee);
            return this;
        }

        public ReservationSerializeMapperBuilder reservedFrom(String reservedFrom) {
            mapper.setReservedFrom(reservedFrom);
            return this;
        }

        public ReservationSerializeMapperBuilder reservedTo(String reservedTo) {
            mapper.setReservedTo(reservedTo);
            return this;
        }

        public ReservationSerializeMapperBuilder reason(String reason) {
            mapper.setReason(reason);
            return this;
        }

        public ReservationSerializeMapper build() {
            return mapper;
        }
    }
}
