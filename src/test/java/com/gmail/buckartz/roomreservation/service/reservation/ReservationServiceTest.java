package com.gmail.buckartz.roomreservation.service.reservation;

import com.gmail.buckartz.roomreservation.config.UnitTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Employee;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.service.room.RoomSaveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@UnitTestConfiguration
@RunWith(SpringRunner.class)
public class ReservationServiceTest {
    @Autowired
    private ReservationSaveService reservationSaveService;

    @Autowired
    private RoomSaveService roomSaveService;
    @Autowired
    private ReservationGetCountByRoomAndPeriodService byRoomAndPeriodService;


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

    @Test
    public void getCountReservationInPeriod() {
        Room room = Room.builder()
                .number("1111")
                .build();
        roomSaveService.save(room);
        Reservation reservation1 = Reservation.builder()
                .room(room)
                .employee(Employee.builder()
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .build())
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-04-04T12:52", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-04-04T13:50", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reason("Dance")
                .build();
        reservationSaveService.save(reservation1);
        Reservation reservation2 = Reservation.builder()
                .room(room)
                .employee(Employee.builder()
                        .firstName("Ivan1")
                        .lastName("Ivanov1")
                        .build())
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-04-04T11:52", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-05-04T14:50", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reason("Dance1")
                .build();
        reservationSaveService.save(reservation2);

        int aLong = byRoomAndPeriodService.execute(room.getId(),
                Timestamp.valueOf(LocalDateTime.parse("2018-04-04T12:55", DateTimeFormatter.ISO_LOCAL_DATE_TIME)),
                Timestamp.valueOf(LocalDateTime.parse("2018-04-04T13:31", DateTimeFormatter.ISO_LOCAL_DATE_TIME)));

        System.out.println(aLong);
        assertEquals(2, aLong);
    }
}