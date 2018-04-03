package com.gmail.buckartz.roomreservation.controller.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.buckartz.roomreservation.config.ControllerTestContext;
import com.gmail.buckartz.roomreservation.config.IntegrationWebTestConfiguration;
import com.gmail.buckartz.roomreservation.mapping.room.RoomMapping;
import com.gmail.buckartz.roomreservation.mapping.room.mapper.RoomMapper;
import com.gmail.buckartz.roomreservation.service.room.RoomSaveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationWebTestConfiguration
@RunWith(SpringRunner.class)
public class RoomControllerTests extends ControllerTestContext {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomSaveService roomSaveService;

    @Autowired
    private RoomMapping roomMapping;

    @Test
    public void roomSave() throws Exception {
        RoomMapper roomMapper = RoomMapper.builder()
                .number("1408")
                .sitsCount(2)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(post("/room")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(objectMapper.writeValueAsString(roomMapper)))
                .andExpect(status().isCreated())
                .andDo(getDocumentHandler()
                        .document(
                                requestFields(
                                        fieldWithPath("number").description(""),
                                        fieldWithPath("sitsCount").description("")
                                )));
    }

    @Test
    public void roomSaveWithNotUniqueNumber() throws Exception {
        RoomMapper roomMapper = RoomMapper.builder()
                .number("1408")
                .sitsCount(2)
                .build();
        roomSaveService.save(roomMapping.toObject(roomMapper));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(post("/room")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(objectMapper.writeValueAsString(roomMapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.number", containsInAnyOrder("Room with such number has already existed")));
    }

    @Test
    public void roomSaveWithEmptyNumber() throws Exception {
        RoomMapper roomMapper = RoomMapper.builder()
                .sitsCount(2)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(post("/room")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(objectMapper.writeValueAsString(roomMapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.number", containsInAnyOrder("Room number must not be empty")));
    }

    @Test
    public void roomSaveWithGraterSizeNumber() throws Exception {
        RoomMapper roomMapper = RoomMapper.builder()
                .number("1408abc")
                .sitsCount(2)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(post("/room")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(objectMapper.writeValueAsString(roomMapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.number", containsInAnyOrder("Size of room number must be less than 6 characters")));
    }

    @Test
    public void roomGetAll() throws Exception {
        RoomMapper roomMapper1 = RoomMapper.builder()
                .number("1408abc")
                .sitsCount(2)
                .build();
        RoomMapper roomMapper2 = RoomMapper.builder()
                .number("1408ac")
                .sitsCount(2)
                .build();
        RoomMapper roomMapper3 = RoomMapper.builder()
                .number("1408a")
                .sitsCount(2)
                .build();
        roomSaveService.save(roomMapping.toObject(roomMapper1));
        roomSaveService.save(roomMapping.toObject(roomMapper2));
        roomSaveService.save(roomMapping.toObject(roomMapper3));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(get("/room")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers))
                .andExpect(status().isOk())
                .andDo(getDocumentHandler()
                        .document(
                                responseFields(
                                        fieldWithPath("[].id").description(""),
                                        fieldWithPath("[].number").description(""),
                                        fieldWithPath("[].sitsCount").description(""))));
    }
}
