package com.gmail.buckartz.roomreservation.validation.employee;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LoginUniqueValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUniqueConstraint {
    String message() default "{LoginUniqueConstraint.employeeDeserializeMapper.login}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
