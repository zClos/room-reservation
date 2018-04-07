package com.gmail.buckartz.roomreservation.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RequestMapping(headers = {"Accept-Charset=utf-8"},
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultHeaderValues {
}
