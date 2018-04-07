package com.gmail.buckartz.roomreservation.controller.room;

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

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationWebTestConfiguration
@RunWith(SpringRunner.class)
public class RoomGetControllerTests extends ControllerTestContext {
    @Autowired
    private RoomSaveService roomSaveService;

    @Autowired
    private RoomMapping roomMapping;

    @Test
    public void getAllRoom() throws Exception {
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
                .andExpect(jsonPath("$.length()", is(3)))
                .andDo(getDocumentHandler()
                        .document(
                                responseFields(
                                        fieldWithPath("[].id").description(""),
                                        fieldWithPath("[].number").description(""),
                                        fieldWithPath("[].sitsCount").description(""))));
    }
}
