package com.gmail.buckartz.roomreservation.controller.reservations;

import com.gmail.buckartz.roomreservation.config.ControllerTestContext;
import com.gmail.buckartz.roomreservation.config.IntegrationWebTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.domain.employee.Employee;
import com.gmail.buckartz.roomreservation.mapping.employee.EmployeeDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeDeserializeMapper;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeService;
import com.gmail.buckartz.roomreservation.service.reservation.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.parse;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationWebTestConfiguration
@RunWith(SpringRunner.class)
public class ReservationGetControllerTests extends ControllerTestContext {
    private final static String LOGIN = "my_login";
    private final static String PASSWORD = "password";
    private Employee employee;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private EmployeeDeserializeMapping employeeDeserializeMapping;

    @Before
    public void saveEmployee() {
        EmployeeDeserializeMapper mapper = EmployeeDeserializeMapper.builder()
                .firstName("Alex")
                .lastName("Pit")
                .login(LOGIN)
                .password(PASSWORD)
                .build();
        employee = employeeDeserializeMapping.toObject(mapper);
        employeeService.save(employee);
    }

    @Test
    public void getAllEmployeeRoomReservations() throws Exception {
        Reservation reservation1 = Reservation.builder()
                .room(Room.builder()
                        .number("12")
                        .build())
                .employee(employee)
                .reservedFrom(valueOf(parse("2018-04-04T12:52")))
                .reservedTo(valueOf(parse("2018-04-04T13:50")))
                .reason("Dance")
                .build();
        reservationService.save(reservation1);

        Reservation reservation2 = Reservation.builder()
                .room(Room.builder()
                        .number("13")
                        .build())
                .employee(employee)
                .reservedFrom(valueOf(parse("2018-04-04T11:52")))
                .reservedTo(valueOf(parse("2018-05-04T14:50")))
                .reason("Dance Too")
                .build();
        reservationService.save(reservation2);

        getMockMvc().perform(get("/employee/{id}/reservation", employee.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andDo(getDocumentHandler()
                        .document(
                                responseFields(
                                        fieldWithPath("[].id").description(""),
                                        fieldWithPath("[].room.id").description(""),
                                        fieldWithPath("[].room.number").description(""),
                                        fieldWithPath("[].room.sitsCount").description(""),
                                        fieldWithPath("[].employee.id").description(""),
                                        fieldWithPath("[].employee.firstName").description(""),
                                        fieldWithPath("[].employee.lastName").description(""),
                                        fieldWithPath("[].reservedFrom").description(""),
                                        fieldWithPath("[].reservedTo").description(""),
                                        fieldWithPath("[].reason").description(""))));
    }

    @Test
    public void getAllEmployeeRoomReservationsNotExistsEmployee() throws Exception {
        Reservation reservation1 = Reservation.builder()
                .room(Room.builder()
                        .number("12")
                        .build())
                .employee(employee)
                .reservedFrom(valueOf(parse("2018-04-04T12:52")))
                .reservedTo(valueOf(parse("2018-04-04T13:50")))
                .reason("Dance")
                .build();
        reservationService.save(reservation1);

        Reservation reservation2 = Reservation.builder()
                .room(Room.builder()
                        .number("13")
                        .build())
                .employee(employee)
                .reservedFrom(valueOf(parse("2018-04-04T11:52")))
                .reservedTo(valueOf(parse("2018-05-04T14:50")))
                .reason("Dance Too")
                .build();
        reservationService.save(reservation2);

        getMockMvc().perform(get("/employee/{id}/reservation", employee.getId() + 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.commonViolations",
                        containsInAnyOrder("Employee with id " + (employee.getId() + 1) + " doesn't exist")));
    }

    @Test
    public void getAllEmployeeRoomReservationsByPeriodAndOrderByDesc() throws Exception {
        Reservation reservation1 = Reservation.builder()
                .room(Room.builder()
                        .number("12")
                        .build())
                .employee(employee)
                .reservedFrom(valueOf(parse("2018-04-04T11:52")))
                .reservedTo(valueOf(parse("2018-04-04T13:50")))
                .reason("Dance")
                .build();
        reservationService.save(reservation1);

        Reservation reservation2 = Reservation.builder()
                .room(Room.builder()
                        .number("13")
                        .build())
                .employee(employee)
                .reservedFrom(valueOf(parse("2018-05-04T11:52")))
                .reservedTo(valueOf(parse("2018-05-04T14:50")))
                .reason("Dance Too")
                .build();
        reservationService.save(reservation2);

        getMockMvc().perform(get("/employee/{id}/reservation", employee.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD))
                .param("from", "2018-04-04T12:00")
                .param("to", "2018-05-04T12:00")
                .param("order", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.[0].id", is(reservation2.getId().intValue())))
                .andDo(getDocumentHandler()
                        .document(
                                responseFields(
                                        fieldWithPath("[].id").description(""),
                                        fieldWithPath("[].room.id").description(""),
                                        fieldWithPath("[].room.number").description(""),
                                        fieldWithPath("[].room.sitsCount").description(""),
                                        fieldWithPath("[].employee.id").description(""),
                                        fieldWithPath("[].employee.firstName").description(""),
                                        fieldWithPath("[].employee.lastName").description(""),
                                        fieldWithPath("[].reservedFrom").description(""),
                                        fieldWithPath("[].reservedTo").description(""),
                                        fieldWithPath("[].reason").description(""))));
    }

    @Test
    public void getAllEmployeeRoomReservationsInvalidDate() throws Exception {
        getMockMvc().perform(get("/employee/{id}/reservation", employee.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD))
                .param("from", "2018-04-04{T}12:00")
                .param("to", "2018-15-04T12:00")
                .param("order", "DESC"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.commonViolations[*]",
                        containsInAnyOrder(
                                "Invalid format for 2018-15-04T12:00, follow the ISO template - YYYY-MM-DD{T}HH:MM but drop braces",
                                "Invalid format for 2018-04-04{T}12:00, follow the ISO template - YYYY-MM-DD{T}HH:MM but drop braces"
                        )));
    }
}
