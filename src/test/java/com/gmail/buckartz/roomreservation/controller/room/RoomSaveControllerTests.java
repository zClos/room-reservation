package com.gmail.buckartz.roomreservation.controller.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.buckartz.roomreservation.config.ControllerTestContext;
import com.gmail.buckartz.roomreservation.config.IntegrationWebTestConfiguration;
import com.gmail.buckartz.roomreservation.mapping.employee.EmployeeDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeDeserializeMapper;
import com.gmail.buckartz.roomreservation.mapping.room.RoomDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.room.mapper.RoomDeserializeMapper;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeService;
import com.gmail.buckartz.roomreservation.service.room.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationWebTestConfiguration
@RunWith(SpringRunner.class)
public class RoomSaveControllerTests extends ControllerTestContext {
    private final static String LOGIN = "my_login";
    private final static String PASSWORD = "password";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomDeserializeMapping roomMapping;
    @Autowired
    private EmployeeService employeeService;

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
        employeeService.save(employeeDeserializeMapping.toObject(mapper));
    }

    @Test
    public void saveRoom() throws Exception {
        RoomDeserializeMapper roomDeserializeMapper = RoomDeserializeMapper.builder()
                .number("1408")
                .sitsCount(2)
                .build();

        getMockMvc().perform(post("/room")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD))
                .content(objectMapper.writeValueAsString(roomDeserializeMapper)))
                .andExpect(status().isCreated())
                .andDo(getDocumentHandler()
                        .document(
                                requestFields(
                                        fieldWithPath("number").description(""),
                                        fieldWithPath("sitsCount").description("")
                                )));
    }

    @Test
    public void saveRoomWithNotUniqueNumber() throws Exception {
        RoomDeserializeMapper roomDeserializeMapper = RoomDeserializeMapper.builder()
                .number("1408")
                .sitsCount(2)
                .build();
        roomService.save(roomMapping.toObject(roomDeserializeMapper));

        getMockMvc().perform(post("/room")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD))
                .content(objectMapper.writeValueAsString(roomDeserializeMapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.number",
                        containsInAnyOrder("Room with such number has already existed")));
    }

    @Test
    public void saveRoomWithEmptyNumber() throws Exception {
        RoomDeserializeMapper roomDeserializeMapper = RoomDeserializeMapper.builder()
                .sitsCount(2)
                .build();

        getMockMvc().perform(post("/room")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD))
                .content(objectMapper.writeValueAsString(roomDeserializeMapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.number",
                        containsInAnyOrder("Room number must not be empty")));
    }

    @Test
    public void saveRoomWithGraterSizeNumber() throws Exception {
        RoomDeserializeMapper roomDeserializeMapper = RoomDeserializeMapper.builder()
                .number("1408abc")
                .sitsCount(2)
                .build();

        getMockMvc().perform(post("/room")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD))
                .content(objectMapper.writeValueAsString(roomDeserializeMapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.number",
                        containsInAnyOrder("Size of room number must be less than 6 characters")));
    }
}
