package com.gmail.buckartz.roomreservation.mapping.reservation.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmail.buckartz.roomreservation.validation.reservations.ReservationPeriodConstraint;
import com.gmail.buckartz.roomreservation.validation.reservations.WorkTimeConstraint;
import com.gmail.buckartz.roomreservation.validation.room.RoomIdExistenceConstraint;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@GroupSequence({ReservationMapper.class,
        ReservationMapper.CorrectValuesGroup.class,
        ReservationMapper.WorkRegimeGroup.class,
        ReservationMapper.ReservationPeriodGroup.class})
@ReservationPeriodConstraint(groups = ReservationMapper.ReservationPeriodGroup.class)
public class ReservationMapper {
    @NotNull(message = "{NotNull.ReservationMapper.roomId}")
    @RoomIdExistenceConstraint(groups = CorrectValuesGroup.class)
    private Long roomId;
    @NotEmpty(message = "{NotEmpty.ReservationMapper.reservedFrom}")
    @Pattern(regexp = "^[\\d]{4}[-][\\d]{2}[-][\\d]{2}[T][\\d]{2}[:][\\d]{2}$",
            message = "{Pattern.ReservationMapper.date.time}", groups = CorrectValuesGroup.class)
    @WorkTimeConstraint(on = "09:00", off = "18:00", groups = WorkRegimeGroup.class)
    private String reservedFrom;
    @NotEmpty(message = "{NotEmpty.ReservationMapper.reservedTo}")
    @Pattern(regexp = "^[\\d]{4}[-][\\d]{2}[-][\\d]{2}[T][\\d]{2}[:][\\d]{2}$",
            message = "{Pattern.ReservationMapper.date.time}", groups = CorrectValuesGroup.class)
    @WorkTimeConstraint(on = "09:00", off = "18:00", groups = WorkRegimeGroup.class)
    private String reservedTo;
    @Size(max = 500, message = "{Size.ReservationMapper.reason}")
    private String reason;

    @JsonIgnore
    private Long employeeId;

    public interface CorrectValuesGroup {
    }

    public interface WorkRegimeGroup {
    }

    public interface ReservationPeriodGroup {
    }

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
