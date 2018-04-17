package com.gmail.buckartz.roomreservation.service.reservation;

import com.gmail.buckartz.roomreservation.config.UnitTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.domain.employee.Employee;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeService;
import com.gmail.buckartz.roomreservation.service.reservation.filter.ReservationSearchFilters;
import com.gmail.buckartz.roomreservation.service.room.RoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.parse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@UnitTestConfiguration
@RunWith(SpringRunner.class)
public class ReservationGetServiceTests {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void getAllReservationByEmployeeInPeriod() {
        Employee employee = Employee.builder()
                .firstName("Alen")
                .lastName("Delon")
                .build();
        employeeService.save(employee);

        Reservation reservation1 = Reservation.builder()
                .room(Room.builder()
                        .number("12")
                        .build())
                .employee(employee)
                .reservedFrom(valueOf(parse("2018-05-03T12:52")))
                .reservedTo(valueOf(parse("2018-05-03T13:50")))
                .reason("Dance")
                .build();
        reservationService.save(reservation1);

        Reservation reservation2 = Reservation.builder()
                .room(Room.builder()
                        .number("13")
                        .build())
                .employee(employee)
                .reservedFrom(valueOf(parse("2018-04-04T11:52")))
                .reservedTo(valueOf(parse("2018-04-04T14:50")))
                .reason("Dance Too")
                .build();
        reservationService.save(reservation2);

        Reservation reservation3 = Reservation.builder()
                .room(Room.builder()
                        .number("15")
                        .build())
                .employee(employee)
                .reservedFrom(valueOf(parse("2018-06-04T11:52")))
                .reservedTo(valueOf(parse("2018-06-04T14:50")))
                .reason("Dance Too")
                .build();
        reservationService.save(reservation3);

        ReservationSearchFilters filters = ReservationSearchFilters.builder()
                .id(employee.getId())
                .from("2018-05-03T12:52")
                .to("2018-06-04T14:50")
                .build();
        List<Reservation> reservationList = reservationService.findAllByFilter(filters);

        assertEquals(2, reservationList.size());
        assertTrue(reservationList.get(0).equals(reservation1));
        assertTrue(reservationList.get(1).equals(reservation3));
    }

    @Test
    public void getAllReservationByEmployeeInPeriodOrdered() {
        Employee employee = Employee.builder()
                .firstName("Alen")
                .lastName("Delon")
                .build();
        employeeService.save(employee);

        Reservation reservation1 = Reservation.builder()
                .room(Room.builder()
                        .number("12")
                        .build())
                .employee(employee)
                .reservedFrom(valueOf(parse("2018-05-03T12:52")))
                .reservedTo(valueOf(parse("2018-05-03T13:50")))
                .reason("Dance")
                .build();
        reservationService.save(reservation1);

        Reservation reservation2 = Reservation.builder()
                .room(Room.builder()
                        .number("13")
                        .build())
                .employee(employee)
                .reservedFrom(valueOf(parse("2018-04-04T11:52")))
                .reservedTo(valueOf(parse("2018-04-04T14:50")))
                .reason("Dance Too")
                .build();
        reservationService.save(reservation2);

        Reservation reservation3 = Reservation.builder()
                .room(Room.builder()
                        .number("15")
                        .build())
                .employee(employee)
                .reservedFrom(valueOf(parse("2018-06-04T11:52")))
                .reservedTo(valueOf(parse("2018-06-04T14:50")))
                .reason("Dance Too")
                .build();
        reservationService.save(reservation3);

        ReservationSearchFilters filtersASC = ReservationSearchFilters.builder()
                .id(employee.getId())
                .from("2018-04-04T11:52")
                .to("2018-05-03T13:50")
                .order("asc")
                .build();
        List<Reservation> reservationList = reservationService.findAllByFilter(filtersASC);

        assertEquals(2, reservationList.size());
        assertEquals(reservation2, reservationList.get(0));
        assertEquals(reservation1, reservationList.get(1));


        ReservationSearchFilters filtersDESC = ReservationSearchFilters.builder()
                .id(employee.getId())
                .from("2018-04-04T11:52")
                .to("2018-06-04T14:50")
                .order("desc")
                .build();
        List<Reservation> reservationList2 = reservationService.findAllByFilter(filtersDESC);

        assertEquals(3, reservationList2.size());
        assertEquals(reservation3, reservationList2.get(0));
        assertEquals(reservation1, reservationList2.get(1));
        assertEquals(reservation2, reservationList2.get(2));
    }
}