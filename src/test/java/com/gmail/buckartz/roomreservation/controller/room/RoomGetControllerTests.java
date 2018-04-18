package com.gmail.buckartz.roomreservation.controller.room;

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

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationWebTestConfiguration
@RunWith(SpringRunner.class)
public class RoomGetControllerTests extends ControllerTestContext {
    private final static String LOGIN = "my_login";
    private final static String PASSWORD = "password";

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
    public void getAllRoomDefault() throws Exception {
        RoomDeserializeMapper roomDeserializeMapper1 = RoomDeserializeMapper.builder()
                .number("1408abc")
                .sitsCount(2)
                .build();
        RoomDeserializeMapper roomDeserializeMapper2 = RoomDeserializeMapper.builder()
                .number("1408ac")
                .sitsCount(2)
                .build();
        RoomDeserializeMapper roomDeserializeMapper3 = RoomDeserializeMapper.builder()
                .number("1408a")
                .sitsCount(2)
                .build();
        roomService.save(roomMapping.toObject(roomDeserializeMapper1));
        roomService.save(roomMapping.toObject(roomDeserializeMapper2));
        roomService.save(roomMapping.toObject(roomDeserializeMapper3));

        getMockMvc().perform(get("/room")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", is(3)))
                .andDo(getDocumentHandler()
                        .document(
                                responseFields(
                                        fieldWithPath(".content[].id").description(""),
                                        fieldWithPath(".content[].number").description(""),
                                        fieldWithPath(".content[].sitsCount").description(""),
                                        fieldWithPath(".totalElements").description(""),
                                        fieldWithPath(".totalPages").description(""),
                                        fieldWithPath(".number").description(""),
                                        fieldWithPath(".size").description(""))));
    }

    @Test
    public void getAllRoomPaging() throws Exception {
        RoomDeserializeMapper roomDeserializeMapper1 = RoomDeserializeMapper.builder()
                .number("1408abc")
                .sitsCount(2)
                .build();
        RoomDeserializeMapper roomDeserializeMapper2 = RoomDeserializeMapper.builder()
                .number("1408ac")
                .sitsCount(2)
                .build();
        RoomDeserializeMapper roomDeserializeMapper3 = RoomDeserializeMapper.builder()
                .number("1408a")
                .sitsCount(2)
                .build();
        roomService.save(roomMapping.toObject(roomDeserializeMapper1));
        roomService.save(roomMapping.toObject(roomDeserializeMapper2));
        roomService.save(roomMapping.toObject(roomDeserializeMapper3));

        getMockMvc().perform(get("/room")
                .param("page", "2")
                .param("size", "2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.content.length()", is(1)))
                .andDo(getDocumentHandler()
                        .document(
                                responseFields(
                                        fieldWithPath(".content[].id").description(""),
                                        fieldWithPath(".content[].number").description(""),
                                        fieldWithPath(".content[].sitsCount").description(""),
                                        fieldWithPath(".totalElements").description(""),
                                        fieldWithPath(".totalPages").description(""),
                                        fieldWithPath(".number").description(""),
                                        fieldWithPath(".size").description(""))));
    }
}
