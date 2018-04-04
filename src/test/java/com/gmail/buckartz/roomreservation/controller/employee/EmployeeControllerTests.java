package com.gmail.buckartz.roomreservation.controller.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.buckartz.roomreservation.config.ControllerTestContext;
import com.gmail.buckartz.roomreservation.config.IntegrationWebTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Employee;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeMapper;
import com.gmail.buckartz.roomreservation.mapping.reservation.mapper.ReservationMapper;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeSaveService;
import com.gmail.buckartz.roomreservation.service.room.RoomSaveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
public class EmployeeControllerTests extends ControllerTestContext {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeSaveService employeeSaveService;

    @Autowired
    private RoomSaveService roomSaveService;

    @Test
    public void saveEmployee() throws Exception {
        EmployeeMapper mapper = EmployeeMapper.builder()
                .firstName("Donald")
                .lastName("Tramp")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(post("/employee")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(objectMapper.writeValueAsString(mapper)))
                .andExpect(status().isCreated())
                .andDo(getDocumentHandler()
                        .document(
                                requestFields(
                                        fieldWithPath("firstName").description(""),
                                        fieldWithPath("lastName").description("")
                                )));
    }

    @Test
    public void saveEmployeeWithEmptyValue() throws Exception {
        EmployeeMapper mapper = EmployeeMapper.builder()
                .firstName("")
                .lastName("")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(post("/employee")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(objectMapper.writeValueAsString(mapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.firstName", containsInAnyOrder("Employee first name must not be empty")))
                .andExpect(jsonPath("$.fieldsViolations.lastName", containsInAnyOrder("Employee last name must not be empty")));
    }

    @Test
    public void saveEmployeeWithBadLengthValues() throws Exception {
        EmployeeMapper mapper = EmployeeMapper.builder()
                .firstName("Aaaaaaaaaaddddddddasdds")
                .lastName("Z")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(post("/employee")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(objectMapper.writeValueAsString(mapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.firstName", containsInAnyOrder("Size of employee first name must not be less than 2 and more than 16 characters")))
                .andExpect(jsonPath("$.fieldsViolations.lastName", containsInAnyOrder("Size of employee last name must not be less than 2 and more than 16 characters")));
    }

    @Test
    public void saveEmployeeRoomReservation() throws Exception {
        Employee employee = Employee.builder()
                .firstName("Big")
                .lastName("Boss")
                .build();
        employeeSaveService.save(employee);

        Room room = Room.builder()
                .number("11")
                .sitsCount(5)
                .build();
        roomSaveService.save(room);

        LocalDateTime dateTimeFrom = LocalDateTime.now();
        dateTimeFrom = dateTimeFrom.minusSeconds(dateTimeFrom.getSecond()).minusNanos(dateTimeFrom.getNano());
        LocalDateTime dateTimeTo = dateTimeFrom.minusMinutes(30);

        ReservationMapper mapper = ReservationMapper.builder()
                .roomId(room.getId())
                .reservedFrom(dateTimeFrom.toString())
                .reservedTo(dateTimeTo.toString())
                .reason("Daily Planing")
                .builder();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(post("/employee/{id}/reservation", employee.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
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
    public void saveEmployeeRoomReservationForNotExistOne() throws Exception {
        Employee employee = Employee.builder()
                .firstName("Big")
                .lastName("Boss")
                .build();
        employeeSaveService.save(employee);

        Room room = Room.builder()
                .number("11")
                .sitsCount(5)
                .build();
        roomSaveService.save(room);

        LocalDateTime dateTimeFrom = LocalDateTime.now();
        dateTimeFrom = dateTimeFrom.minusSeconds(dateTimeFrom.getSecond()).minusNanos(dateTimeFrom.getNano());
        LocalDateTime dateTimeTo = dateTimeFrom.minusMinutes(30);

        ReservationMapper mapper = ReservationMapper.builder()
                .roomId(room.getId())
                .reservedFrom(dateTimeFrom.toString())
                .reservedTo(dateTimeTo.toString())
                .reason("Daily Planing")
                .builder();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(post("/employee/{id}/reservation", employee.getId() + 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(objectMapper.writeValueAsString(mapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.commonViolations", containsInAnyOrder("Employee with id " + (employee.getId() + 1) + " doesn't exist")));
    }

    @Test
    public void saveEmployeeRoomReservationForNotExistRoom() throws Exception {
        Employee employee = Employee.builder()
                .firstName("Big")
                .lastName("Boss")
                .build();
        employeeSaveService.save(employee);

        Room room = Room.builder()
                .number("11")
                .sitsCount(5)
                .build();
        roomSaveService.save(room);

        LocalDateTime dateTimeFrom = LocalDateTime.now();
        dateTimeFrom = dateTimeFrom.minusSeconds(dateTimeFrom.getSecond()).minusNanos(dateTimeFrom.getNano());
        LocalDateTime dateTimeTo = dateTimeFrom.minusMinutes(30);

        ReservationMapper mapper = ReservationMapper.builder()
                .roomId(room.getId() + 1)
                .reservedFrom(dateTimeFrom.toString())
                .reservedTo(dateTimeTo.toString())
                .reason("Daily Planing")
                .builder();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(post("/employee/{id}/reservation", employee.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(objectMapper.writeValueAsString(mapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.roomId",
                        containsInAnyOrder("Room with id " + (room.getId() + 1) + " doesn't exist")));
    }
}
