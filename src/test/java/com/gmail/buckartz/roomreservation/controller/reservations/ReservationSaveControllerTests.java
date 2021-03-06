package com.gmail.buckartz.roomreservation.controller.reservations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.buckartz.roomreservation.config.ControllerTestContext;
import com.gmail.buckartz.roomreservation.config.IntegrationWebTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.domain.employee.Employee;
import com.gmail.buckartz.roomreservation.mapping.employee.EmployeeDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeDeserializeMapper;
import com.gmail.buckartz.roomreservation.mapping.reservation.ReservationDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.reservation.mapper.ReservationDeserializeMapper;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeService;
import com.gmail.buckartz.roomreservation.service.reservation.ReservationService;
import com.gmail.buckartz.roomreservation.service.room.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationWebTestConfiguration
@RunWith(SpringRunner.class)
public class ReservationSaveControllerTests extends ControllerTestContext {
    private final static String LOGIN = "my_login";
    private final static String PASSWORD = "password";
    private Employee employee;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationDeserializeMapping reservationMapping;

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
    public void saveEmployeeRoomReservation() throws Exception {
        Room room = Room.builder()
                .number("11")
                .sitsCount(5)
                .build();
        roomService.save(room);

        LocalDateTime dateTimeFrom = getTestLocalDateTime();
        LocalDateTime dateTimeTo = dateTimeFrom.plusMinutes(30);

        ReservationDeserializeMapper mapper = ReservationDeserializeMapper.builder()
                .roomId(room.getId())
                .reservedFrom(dateTimeFrom.toString())
                .reservedTo(dateTimeTo.toString())
                .reason("Daily Planing")
                .builder();

        getMockMvc().perform(post("/employee/{id}/reservation", employee.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD))
                .content(objectMapper.writeValueAsString(mapper)))
                .andExpect(status().isCreated())
                .andDo(getDocumentHandler()
                        .document(
                                requestFields(
                                        fieldWithPath("roomId").description(""),
                                        fieldWithPath("reservedFrom").description(""),
                                        fieldWithPath("reservedTo").description(""),
                                        fieldWithPath("reason").description("")
                                )));
    }

    @Test
    public void saveEmployeeRoomReservationForNotExistEmployee() throws Exception {
        Room room = Room.builder()
                .number("11")
                .sitsCount(5)
                .build();
        roomService.save(room);

        LocalDateTime dateTimeFrom = getTestLocalDateTime();
        LocalDateTime dateTimeTo = dateTimeFrom.plusMinutes(30);

        ReservationDeserializeMapper mapper = ReservationDeserializeMapper.builder()
                .roomId(room.getId())
                .reservedFrom(dateTimeFrom.toString())
                .reservedTo(dateTimeTo.toString())
                .reason("Daily Planing")
                .builder();

        getMockMvc().perform(post("/employee/{id}/reservation", employee.getId() + 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD))
                .content(objectMapper.writeValueAsString(mapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.commonViolations",
                        containsInAnyOrder("Employee with id " + (employee.getId() + 1) + " doesn't exist")));
    }

    @Test
    public void saveEmployeeRoomReservationForNotExistRoom() throws Exception {
        Room room = Room.builder()
                .number("11")
                .sitsCount(5)
                .build();
        roomService.save(room);

        LocalDateTime dateTimeFrom = getTestLocalDateTime();
        LocalDateTime dateTimeTo = dateTimeFrom.plusMinutes(30);

        ReservationDeserializeMapper mapper = ReservationDeserializeMapper.builder()
                .roomId(room.getId() + 1)
                .reservedFrom(dateTimeFrom.toString())
                .reservedTo(dateTimeTo.toString())
                .reason("Daily Planing")
                .builder();

        getMockMvc().perform(post("/employee/{id}/reservation", employee.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD))
                .content(objectMapper.writeValueAsString(mapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.roomId",
                        containsInAnyOrder("Room with id " + (room.getId() + 1) + " doesn't exist")));
    }

    @Test
    public void saveEmployeeRoomReservationEmptyValues() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 500; ) {
            stringBuilder.append(++i);
        }

        ReservationDeserializeMapper mapper = ReservationDeserializeMapper.builder()
                .roomId(null)
                .reservedFrom("")
                .reservedTo("")
                .reason(stringBuilder.toString())
                .builder();

        getMockMvc().perform(post("/employee/{id}/reservation", employee.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD))
                .content(objectMapper.writeValueAsString(mapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.roomId",
                        containsInAnyOrder("Room id can't be empty")))
                .andExpect(jsonPath("$.fieldsViolations.reservedFrom",
                        containsInAnyOrder("Field \'reservedFrom\' can't be empty")))
                .andExpect(jsonPath("$.fieldsViolations.reservedTo",
                        containsInAnyOrder("Field \'reservedTo\' can't be empty")))
                .andExpect(jsonPath("$.fieldsViolations.reason",
                        containsInAnyOrder("Text field shouldn't be more than 500 characters")));
    }

    @Test
    public void saveEmployeeRoomReservationCorrectValuesGroup() throws Exception {
        ReservationDeserializeMapper mapper = ReservationDeserializeMapper.builder()
                .roomId(25L)
                .reservedFrom("2018-02-31R18:42")
                .reservedTo("2018-20-35R12:42")
                .reason("")
                .builder();

        getMockMvc().perform(post("/employee/{id}/reservation", employee.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD))
                .content(objectMapper.writeValueAsString(mapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.roomId",
                        containsInAnyOrder("Room with id 25 doesn't exist")))
                .andExpect(jsonPath("$.fieldsViolations.reservedFrom",
                        containsInAnyOrder("Invalid format for 2018-02-31R18:42, follow the ISO template - YYYY-MM-DD{T}HH:MM but drop braces")))
                .andExpect(jsonPath("$.fieldsViolations.reservedTo",
                        containsInAnyOrder("Invalid format for 2018-20-35R12:42, follow the ISO template - YYYY-MM-DD{T}HH:MM but drop braces")));
    }

    @Test
    public void saveEmployeeRoomReservationOutOfWorkTime() throws Exception {
        Room room = Room.builder()
                .number("11")
                .sitsCount(5)
                .build();
        roomService.save(room);

        ReservationDeserializeMapper mapper = ReservationDeserializeMapper.builder()
                .roomId(room.getId())
                .reservedFrom("2018-04-07T10:00")
                .reservedTo("2018-04-06T18:01")
                .reason("")
                .builder();

        getMockMvc().perform(post("/employee/{id}/reservation", employee.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD))
                .content(objectMapper.writeValueAsString(mapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.reservedFrom",
                        containsInAnyOrder("Bad request time 2018-04-07T10:00 - company works from 09:00 to 18:00 everyday except [SATURDAY, SUNDAY]")))
                .andExpect(jsonPath("$.fieldsViolations.reservedTo",
                        containsInAnyOrder("Bad request time 2018-04-06T18:01 - company works from 09:00 to 18:00 everyday except [SATURDAY, SUNDAY]")));
    }

    @Test
    public void saveEmployeeRoomReservationInExistPeriod() throws Exception {
        Room room = Room.builder()
                .number("11")
                .sitsCount(5)
                .build();
        roomService.save(room);

        LocalDateTime dateTime = getTestLocalDateTime();

        ReservationDeserializeMapper mapper = ReservationDeserializeMapper.builder()
                .roomId(room.getId())
                .reservedFrom(dateTime.toString())
                .reservedTo(dateTime.plusHours(1).toString())
                .reason("")
                .employeeId(employee.getId())
                .builder();
        reservationService.save(reservationMapping.toObject(mapper));

        mapper = ReservationDeserializeMapper.builder(mapper)
                .reservedFrom(dateTime.plusMinutes(15).toString())
                .reservedTo(dateTime.plusHours(2).toString())
                .builder();

        getMockMvc().perform(post("/employee/{id}/reservation", employee.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD))
                .content(objectMapper.writeValueAsString(mapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.commonViolations",
                        containsInAnyOrder("Room has already reserved for that period, or period is bad")));
    }
}
