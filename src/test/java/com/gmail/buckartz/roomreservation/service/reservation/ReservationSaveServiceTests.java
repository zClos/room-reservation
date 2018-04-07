package com.gmail.buckartz.roomreservation.service.reservation;

import com.gmail.buckartz.roomreservation.config.UnitTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Employee;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.domain.Room;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertNotNull;

@UnitTestConfiguration
@RunWith(SpringRunner.class)
public class ReservationSaveServiceTests {
    @Autowired
    private ReservationSaveService reservationSaveService;

    @Test
    public void saveReservation() {
        Reservation reservation = Reservation.builder()
                .room(Room.builder()
                        .number("1103")
                        .sitsCount(22)
                        .build())
                .employee(Employee.builder()
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .build())
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-04-04T12:52", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-04-04T13:50", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reason("Dance")
                .build();

        reservationSaveService.save(reservation);

        assertNotNull(reservation.getId());
        assertNotNull(reservation.getRoom().getId());
        assertNotNull(reservation.getEmployee().getId());
    }
}
