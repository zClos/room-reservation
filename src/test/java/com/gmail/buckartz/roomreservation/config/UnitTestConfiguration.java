package com.gmail.buckartz.roomreservation.config;

import com.gmail.buckartz.roomreservation.RoomReservationApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SpringBootTest(classes = RoomReservationApplication.class)
@ActiveProfiles("test")
@Transactional
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UnitTestConfiguration {
}
