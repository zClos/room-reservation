package com.gmail.buckartz.roomreservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.buckartz.roomreservation.config.ControllerTestContext;
import com.gmail.buckartz.roomreservation.config.IntegrationWebTestConfiguration;
import com.gmail.buckartz.roomreservation.mapping.room.mapper.RoomMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationWebTestConfiguration
@RunWith(SpringRunner.class)
public class RoomControllerTests extends ControllerTestContext {

    @Autowired
    private ObjectMapper objectMapper;


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
                .content(objectMapper.writeValueAsBytes(roomMapper)))
                .andExpect(status().isCreated())
                .andDo(document(""));
    }
}
