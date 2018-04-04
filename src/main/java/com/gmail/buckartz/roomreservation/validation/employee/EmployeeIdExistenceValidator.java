package com.gmail.buckartz.roomreservation.validation.employee;

import com.gmail.buckartz.roomreservation.service.employee.EmployeeGetByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class EmployeeIdExistenceValidator implements ConstraintValidator<EmployeeIdExistenceConstraint, Long> {
    @Autowired
    private EmployeeGetByIdService getByIdService;

    @Override
    public void initialize(EmployeeIdExistenceConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return getByIdService.getById(value) != null;
    }
}
