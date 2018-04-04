package com.gmail.buckartz.roomreservation.mapping.reservation.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationMapper {
    private Long roomId;
    private String reservedFrom;
    private String reservedTo;
    private String reason;

    @JsonIgnore
    private Long employeeId;

    public static ReservationMapperBuilder builder() {
        return new ReservationMapperBuilder();
    }

    public static ReservationMapperBuilder builder(ReservationMapper reservationMapper) {
        return new ReservationMapperBuilder(reservationMapper);
    }

    public static class ReservationMapperBuilder {
        private ReservationMapper mapper;

        public ReservationMapperBuilder() {
            mapper = new ReservationMapper();
        }

        public ReservationMapperBuilder(ReservationMapper reservationMapper) {
            mapper = reservationMapper;
        }

        public ReservationMapperBuilder roomId(Long roomId) {
            mapper.setRoomId(roomId);
            return this;
        }

        public ReservationMapperBuilder employeeId(Long employeeId) {
            mapper.setEmployeeId(employeeId);
            return this;
        }

        public ReservationMapperBuilder reservedFrom(String reservedFrom) {
            mapper.setReservedFrom(reservedFrom);
            return this;
        }

        public ReservationMapperBuilder reservedTo(String reservedTo) {
            mapper.setReservedTo(reservedTo);
            return this;
        }

        public ReservationMapperBuilder reason(String reason) {
            mapper.setReason(reason);
            return this;
        }

        public ReservationMapper builder() {
            return mapper;
        }
    }
}
