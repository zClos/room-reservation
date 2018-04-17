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
@GroupSequence({ReservationDeserializeMapper.class,
        ReservationDeserializeMapper.CorrectValuesGroup.class,
        ReservationDeserializeMapper.WorkRegimeGroup.class,
        ReservationDeserializeMapper.ReservationPeriodGroup.class})
@ReservationPeriodConstraint(groups = ReservationDeserializeMapper.ReservationPeriodGroup.class)
public class ReservationDeserializeMapper {
    @NotNull(message = "{NotNull.ReservationDeserializeMapper.roomId}")
    @RoomIdExistenceConstraint(groups = CorrectValuesGroup.class)
    private Long roomId;
    @NotEmpty(message = "{NotEmpty.ReservationDeserializeMapper.reservedFrom}")
    @Pattern(regexp = "^[\\d]{4}[-][\\d]{2}[-][\\d]{2}[T][\\d]{2}[:][\\d]{2}$",
            message = "{Pattern.ReservationDeserializeMapper.date.time}", groups = CorrectValuesGroup.class)
    @WorkTimeConstraint(on = "09:00", off = "18:00", groups = WorkRegimeGroup.class)
    private String reservedFrom;
    @NotEmpty(message = "{NotEmpty.ReservationDeserializeMapper.reservedTo}")
    @Pattern(regexp = "^[\\d]{4}[-][\\d]{2}[-][\\d]{2}[T][\\d]{2}[:][\\d]{2}$",
            message = "{Pattern.ReservationDeserializeMapper.date.time}", groups = CorrectValuesGroup.class)
    @WorkTimeConstraint(on = "09:00", off = "18:00", groups = WorkRegimeGroup.class)
    private String reservedTo;
    @Size(max = 500, message = "{Size.ReservationDeserializeMapper.reason}")
    private String reason;

    @JsonIgnore
    private Long employeeId;

    public interface CorrectValuesGroup {
    }

    public interface WorkRegimeGroup {
    }

    public interface ReservationPeriodGroup {
    }

    public static ReservationDeserializeMapperBuilder builder() {
        return new ReservationDeserializeMapperBuilder();
    }

    public static ReservationDeserializeMapperBuilder builder(ReservationDeserializeMapper reservationDeserializeMapper) {
        return new ReservationDeserializeMapperBuilder(reservationDeserializeMapper);
    }

    public static class ReservationDeserializeMapperBuilder {
        private ReservationDeserializeMapper mapper;

        private ReservationDeserializeMapperBuilder() {
            mapper = new ReservationDeserializeMapper();
        }

        private ReservationDeserializeMapperBuilder(ReservationDeserializeMapper reservationDeserializeMapper) {
            mapper = reservationDeserializeMapper;
        }

        public ReservationDeserializeMapperBuilder roomId(Long roomId) {
            mapper.setRoomId(roomId);
            return this;
        }

        public ReservationDeserializeMapperBuilder employeeId(Long employeeId) {
            mapper.setEmployeeId(employeeId);
            return this;
        }

        public ReservationDeserializeMapperBuilder reservedFrom(String reservedFrom) {
            mapper.setReservedFrom(reservedFrom);
            return this;
        }

        public ReservationDeserializeMapperBuilder reservedTo(String reservedTo) {
            mapper.setReservedTo(reservedTo);
            return this;
        }

        public ReservationDeserializeMapperBuilder reason(String reason) {
            mapper.setReason(reason);
            return this;
        }

        public ReservationDeserializeMapper builder() {
            return mapper;
        }
    }
}
