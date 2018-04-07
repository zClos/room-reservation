package com.gmail.buckartz.roomreservation.validation.reservations;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.time.LocalDateTime.parse;

public class DateFormatValidator implements ConstraintValidator<DateFormatConstraint, String> {
    private boolean required;

    @Override
    public void initialize(DateFormatConstraint constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required && value == null) {
            return false;
        }
        if (value == null) {
            return true;
        } else {
            try {
                parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                return true;
            } catch (DateTimeParseException e) {
                return false;
            }
        }
    }
}
