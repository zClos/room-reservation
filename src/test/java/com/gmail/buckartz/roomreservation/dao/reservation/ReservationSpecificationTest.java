package com.gmail.buckartz.roomreservation.dao.reservation;

import com.gmail.buckartz.roomreservation.config.UnitTestConfiguration;
import com.gmail.buckartz.roomreservation.dao.room.RoomRepository;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.domain.employee.Employee;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ReservationEqualSpecification.employeeId;
import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ReservationSoftBetweenSpecification.reservedFromBetween;
import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ReservationSoftBetweenSpecification.reservedToBetween;
import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ResevationCombinedOperatorsSpecifications.andSpecifications;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@UnitTestConfiguration
@RunWith(SpringRunner.class)
public class ReservationSpecificationTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void findOneByEmployeeId() {
        Room room = Room.builder().number("111").sitsCount(25).build();
        roomRepository.save(room);
        Employee employee = Employee.builder().firstName("Ivan").lastName("Ivanov").build();
        employeeService.save(employee);

        Reservation reservation = Reservation.builder()
                .room(room)
                .employee(employee)
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-01-25T00:00")))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-01-26T00:00")))
                .reason("op op")
                .build();
        reservationRepository.save(reservation);

        Reservation getReservation = reservationRepository.findOne(
                employeeId(employee.getId()));

        Assert.assertNotNull(getReservation);
        assertEquals(reservation.getId(), getReservation.getId());
    }

    @Test
    public void findAllByEmployeeIdAndPeriod() {
        Room room = Room.builder().number("111").sitsCount(25).build();
        roomRepository.save(room);
        Employee employee = Employee.builder().firstName("Ivan").lastName("Ivanov").build();
        employeeService.save(employee);

        Reservation reservation1 = Reservation.builder()
                .room(room)
                .employee(employee)
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-01-25T00:00")))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-01-26T00:00")))
                .reason("op op")
                .build();
        reservationRepository.save(reservation1);

        Reservation reservation2 = Reservation.builder()
                .room(room)
                .employee(employee)
                .reservedFrom(Timestamp.valueOf(LocalDateTime.parse("2018-02-25T00:00")))
                .reservedTo(Timestamp.valueOf(LocalDateTime.parse("2018-02-26T00:00")))
                .reason("op op")
                .build();
        reservationRepository.save(reservation2);

        Timestamp from = Timestamp.valueOf(LocalDateTime.of(2018, 2, 20, 0, 0));
        Timestamp to = Timestamp.valueOf(LocalDateTime.of(2018, 3, 22, 0, 0));

        List<Reservation> list = reservationRepository.findAll(
                andSpecifications(
                        employeeId(employee.getId()),
                        reservedFromBetween(from, to),
                        reservedToBetween(from, to))
        );

        assertTrue(list.contains(reservation2));
        assertFalse(list.contains(reservation1));
    }
}
