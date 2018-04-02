package com.gmail.buckartz.roomreservation.controller.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.buckartz.roomreservation.config.ControllerTestContext;
import com.gmail.buckartz.roomreservation.config.IntegrationWebTestConfiguration;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
public class EmployeeControllerTests extends ControllerTestContext {
    @Autowired
    private ObjectMapper objectMapper;

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
}
