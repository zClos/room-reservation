package com.gmail.buckartz.roomreservation.validation.reservations;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WorkTimeValidator implements ConstraintValidator<WorkTimeConstraint, String> {
    private Set<DayOfWeek> weekend;
    private LocalTime timeOn;
    private LocalTime timeOff;

    @Override
    public void initialize(WorkTimeConstraint constraintAnnotation) {
        try {
            weekend = Arrays.stream(constraintAnnotation.weekend()).collect(Collectors.toSet());
            timeOn = LocalTime.parse(constraintAnnotation.on(), DateTimeFormatter.ISO_LOCAL_TIME);
            timeOff = LocalTime.parse(constraintAnnotation.off());
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Unable to parse work time " + e.getParsedString()
                    + " it must has format HH:MM or HH:MM:SS", e.getParsedString(), e.getErrorIndex());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            return false;
        }
        LocalTime localTime = dateTime.toLocalTime();
        return (!weekend.contains(dateTime.getDayOfWeek()) &&
                (timeOn.compareTo(localTime) <= 0
                        && timeOff.compareTo(localTime) >= 0));
    }
}
