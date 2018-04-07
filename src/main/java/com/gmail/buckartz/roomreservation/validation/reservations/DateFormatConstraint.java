package com.gmail.buckartz.roomreservation.validation.reservations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;

@Constraint(validatedBy = {DateFormatValidator.class})
@Target(PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormatConstraint {
    String message() default "{DateFormatConstraint.EmployeeController.date.time}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean required() default true;
}
