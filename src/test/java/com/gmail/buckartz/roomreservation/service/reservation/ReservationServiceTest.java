package com.gmail.buckartz.roomreservation.service.reservation;

import com.gmail.buckartz.roomreservation.config.UnitTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Employee;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeSaveService;
import com.gmail.buckartz.roomreservation.service.reservation.filter.ReservationSearchFilters;
import com.gmail.buckartz.roomreservation.service.room.RoomSaveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@UnitTestConfiguration
@RunWith(SpringRunner.class)
public class ReservationServiceTest {
    @Autowired
    private ReservationSaveService reservationSaveService;

    @Autowired
    private RoomSaveService roomSaveService;
    @Autowired
    private ReservationGetCountByRoomAndPeriodService byRoomAndPeriodService;

    @Autowired
    private EmployeeSaveService employeeSaveService;

    @Autowired
    private ReservationGetAllByEmployeeIdService allByEmployeeIdService;

    @Autowired
    private ReservationGetAllByFilterService getAllByFilterService;

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

    @Test
    public void getAllReservationByEmployee() {
        Employee employee = Employee.builder()
                .firstName("Alen")
                .lastName("Delon")
                .build();
        employeeSaveService.save(employee);

        Reservation reservation1 = Reservation.builder()
                .room(Room.builder()
                        .number("12")
                        .build())
                .employee(employee)
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-04-04T12:52", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-04-04T13:50", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reason("Dance")
                .build();
        reservationSaveService.save(reservation1);

        Reservation reservation2 = Reservation.builder()
                .room(Room.builder()
                        .number("13")
                        .build())
                .employee(employee)
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-04-04T11:52", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-05-04T14:50", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reason("Dance Too")
                .build();
        reservationSaveService.save(reservation2);

        List<Reservation> reservationList = allByEmployeeIdService.getAllByParentId(employee.getId());

        assertEquals(2, reservationList.size());
        assertTrue(reservationList.contains(reservation1));
        assertTrue(reservationList.contains(reservation2));
    }

    @Test
    public void getAllReservationByEmployeeInPeriod() {
        Employee employee = Employee.builder()
                .firstName("Alen")
                .lastName("Delon")
                .build();
        employeeSaveService.save(employee);

        Reservation reservation1 = Reservation.builder()
                .room(Room.builder()
                        .number("12")
                        .build())
                .employee(employee)
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-05-03T12:52")))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-05-03T13:50")))
                .reason("Dance")
                .build();
        reservationSaveService.save(reservation1);

        Reservation reservation2 = Reservation.builder()
                .room(Room.builder()
                        .number("13")
                        .build())
                .employee(employee)
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-04-04T11:52")))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-04-04T14:50")))
                .reason("Dance Too")
                .build();
        reservationSaveService.save(reservation2);

        Reservation reservation3 = Reservation.builder()
                .room(Room.builder()
                        .number("15")
                        .build())
                .employee(employee)
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-06-04T11:52")))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-06-04T14:50")))
                .reason("Dance Too")
                .build();
        reservationSaveService.save(reservation3);

        ReservationSearchFilters filters = ReservationSearchFilters.builder()
                .id(employee.getId())
                .from("2018-05-03T12:52")
                .to("2018-06-04T14:50")
                .build();
        List<Reservation> reservationList = getAllByFilterService.getAllByFilter(filters);

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
        employeeSaveService.save(employee);

        Reservation reservation1 = Reservation.builder()
                .room(Room.builder()
                        .number("12")
                        .build())
                .employee(employee)
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-05-03T12:52", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-05-03T13:50", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reason("Dance")
                .build();
        reservationSaveService.save(reservation1);

        Reservation reservation2 = Reservation.builder()
                .room(Room.builder()
                        .number("13")
                        .build())
                .employee(employee)
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-04-04T11:52", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-04-04T14:50", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reason("Dance Too")
                .build();
        reservationSaveService.save(reservation2);

        Reservation reservation3 = Reservation.builder()
                .room(Room.builder()
                        .number("15")
                        .build())
                .employee(employee)
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-06-04T11:52", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-06-04T14:50", DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .reason("Dance Too")
                .build();
        reservationSaveService.save(reservation3);

        ReservationSearchFilters filtersASC = ReservationSearchFilters.builder()
                .id(employee.getId())
                .from("2018-04-04T11:52")
                .to("2018-05-03T13:50")
                .order("asc")
                .build();
        List<Reservation> reservationList = getAllByFilterService.getAllByFilter(filtersASC);

        assertEquals(2, reservationList.size());
        assertEquals(reservation2, reservationList.get(0));
        assertEquals(reservation1, reservationList.get(1));


        ReservationSearchFilters filtersDESC = ReservationSearchFilters.builder()
                .id(employee.getId())
                .from("2018-04-04T11:52")
                .to("2018-06-04T14:50")
                .order("desc")
                .build();
        List<Reservation> reservationList2 = getAllByFilterService.getAllByFilter(filtersDESC);

        assertEquals(3, reservationList2.size());
        assertEquals(reservation3, reservationList2.get(0));
        assertEquals(reservation1, reservationList2.get(1));
        assertEquals(reservation2, reservationList2.get(2));
    }
}