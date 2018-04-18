package com.gmail.buckartz.roomreservation.controller.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.buckartz.roomreservation.config.ControllerTestContext;
import com.gmail.buckartz.roomreservation.config.IntegrationWebTestConfiguration;
import com.gmail.buckartz.roomreservation.mapping.employee.EmployeeDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeDeserializeMapper;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeService;
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
public class EmployeeSaveControllerTests extends ControllerTestContext {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeDeserializeMapping employeeDeserializeMapping;

    @Test
    public void saveEmployee() throws Exception {
        EmployeeDeserializeMapper mapper = EmployeeDeserializeMapper.builder()
                .firstName("Donald")
                .lastName("Tramp")
                .login("usa-usa")
                .password("rule-rule")
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
                                        fieldWithPath("lastName").description(""),
                                        fieldWithPath("login").description(""),
                                        fieldWithPath("password").description("")
                                )));
    }

    @Test
    public void saveEmployeeWithEmptyValue() throws Exception {
        EmployeeDeserializeMapper mapper = EmployeeDeserializeMapper.builder()
                .firstName("")
                .lastName("")
                .login("")
                .password("")
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
                .andExpect(jsonPath("$.fieldsViolations.lastName", containsInAnyOrder("Employee last name must not be empty")))
                .andExpect(jsonPath("$.fieldsViolations.login", containsInAnyOrder("Login must not be empty")))
                .andExpect(jsonPath("$.fieldsViolations.password", containsInAnyOrder("Password must not be empty")));
    }

    @Test
    public void saveEmployeeWithBadLengthValues() throws Exception {
        EmployeeDeserializeMapper mapper = EmployeeDeserializeMapper.builder()
                .firstName("Aaaaaaaaaaddddddddasdds")
                .lastName("Z")
                .login("qweqqweqweqweqweqwe")
                .password("123")
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
                .andExpect(jsonPath("$.fieldsViolations.lastName", containsInAnyOrder("Size of employee last name must not be less than 2 and more than 16 characters")))
                .andExpect(jsonPath("$.fieldsViolations.login", containsInAnyOrder("Size of login must not be less than 6 and more than 12 characters")))
                .andExpect(jsonPath("$.fieldsViolations.password", containsInAnyOrder("Size of password must not be less than 6 and more than 12 characters")));
    }

    @Test
    public void saveEmployeeWithNotUniqueLogin() throws Exception {
        EmployeeDeserializeMapper mapper = EmployeeDeserializeMapper.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .login("Ivanov69")
                .password("myPass")
                .build();
        employeeService.save(employeeDeserializeMapping.toObject(mapper));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(post("/employee")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(objectMapper.writeValueAsString(mapper)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsViolations.login", containsInAnyOrder("Employee with the same login has already existed")));
    }

}
