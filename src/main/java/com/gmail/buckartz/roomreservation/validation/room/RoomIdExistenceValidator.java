package com.gmail.buckartz.roomreservation.validation.room;

import com.gmail.buckartz.roomreservation.service.room.RoomGetByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class RoomIdExistenceValidator implements ConstraintValidator<RoomIdExistenceConstraint, Long> {
    @Autowired
    private RoomGetByIdService getByIdService;

    @Override
    public void initialize(RoomIdExistenceConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return getByIdService.getById(value) != null;
    }
}
