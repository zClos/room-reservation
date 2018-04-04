package com.gmail.buckartz.roomreservation.validation.employee;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmployeeIdExistenceValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeIdExistenceConstraint {
    String message() default "{EmployeeIdExistenceConstraint.roomController.id}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
