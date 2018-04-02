package com.gmail.buckartz.roomreservation.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ViolationResponse handleException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        Map<String, List<String>> fields = bindingResult.getFieldErrors().stream()
                .collect(
                        groupingBy(FieldError::getField,
                                mapping(FieldError::getDefaultMessage, toList())));

        List<String> common = bindingResult.getGlobalErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(toList());

        return ViolationResponse.builder()
                .fields(fields)
                .common(common)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ViolationResponse handleException(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

        List<String> common = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(toList());

        return ViolationResponse.builder().common(common).build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ViolationResponse handleException(MethodArgumentTypeMismatchException exception) {
        String errorMessage = String.format("Incorrect input value \'%s\' for parameter \'%s\'", exception.getValue(), exception.getName());
        return ViolationResponse.builder().common(errorMessage).build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ViolationResponse handleException(InvalidFormatException exception) {
        String errorMessage = String.format("Incorrect input value \'%s\' for parameter \'%s\'",
                exception.getValue(), exception.getPath().get(0).getFieldName());
        return ViolationResponse.builder().common(errorMessage).build();
    }
}
