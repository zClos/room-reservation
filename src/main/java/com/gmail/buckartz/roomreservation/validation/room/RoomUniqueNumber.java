package com.gmail.buckartz.roomreservation.validation.room;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RoomUniqueNumberValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoomUniqueNumber {
    String message() default "{RoomUniqueNumber.roomDeserializeMapper.number}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
