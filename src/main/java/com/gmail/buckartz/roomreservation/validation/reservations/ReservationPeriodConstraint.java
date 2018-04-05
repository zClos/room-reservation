package com.gmail.buckartz.roomreservation.validation.reservations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ReservationPeriodValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReservationPeriodConstraint {
    String message() default "{ReservationPeriodConstraint.reservation.date.time}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
