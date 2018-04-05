package com.gmail.buckartz.roomreservation.validation.reservations;

import com.gmail.buckartz.roomreservation.mapping.reservation.mapper.ReservationMapper;
import com.gmail.buckartz.roomreservation.service.reservation.ReservationGetCountByRoomAndPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class ReservationPeriodValidator implements ConstraintValidator<ReservationPeriodConstraint, ReservationMapper> {
    @Autowired
    private ReservationGetCountByRoomAndPeriodService getCountByRoomAndPeriodService;

    @Override
    public void initialize(ReservationPeriodConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(ReservationMapper value, ConstraintValidatorContext context) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now().minusMinutes(1));
        Timestamp from = Timestamp.valueOf(LocalDateTime.parse(value.getReservedFrom()));
        Timestamp to = Timestamp.valueOf(LocalDateTime.parse(value.getReservedTo()));
        return from.compareTo(now) >= 0 && to.compareTo(now) > 0 &&
                from.compareTo(to) <= 0 && getCountByRoomAndPeriodService.execute(value.getRoomId(), from, to) == 0;
    }
}
