package com.gmail.buckartz.roomreservation.validation.room;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RoomIdExistenceValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoomIdExistenceConstraint {
    String message() default "{RoomIdExistenceConstraint.roomController.id}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
