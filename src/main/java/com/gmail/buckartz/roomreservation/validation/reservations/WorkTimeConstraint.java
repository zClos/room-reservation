package com.gmail.buckartz.roomreservation.validation.reservations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.DayOfWeek;

@Constraint(validatedBy = WorkTimeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkTimeConstraint {
    String message() default "{WorkTimeConstraint.ReservationDeserializeMapper.date.time}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    DayOfWeek[] weekend() default {DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};

    String on() default "00:00:00";

    String off() default "23:59:59";
}
