package com.gmail.buckartz.roomreservation.validation.room;

import com.gmail.buckartz.roomreservation.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class RoomUniqueNumberValidator implements ConstraintValidator<RoomUniqueNumber, String> {
    @Autowired
    private RoomService roomService;

    @Override
    public void initialize(RoomUniqueNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {
        return roomService.findByNumber(number) == null;
    }
}
