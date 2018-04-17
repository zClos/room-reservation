package com.gmail.buckartz.roomreservation.controller.employee;

import com.gmail.buckartz.roomreservation.config.ControllerTestContext;
import com.gmail.buckartz.roomreservation.config.IntegrationWebTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Employee;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationWebTestConfiguration
@RunWith(SpringRunner.class)
public class EmployeeGetControllerTests extends ControllerTestContext {
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void getEmployeeById() throws Exception {
        Employee employee = Employee.builder()
                .firstName("Alex")
                .lastName("Pit")
                .build();
        employeeService.save(employee);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(get("/employee/{id}", employee.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(employee.getId().intValue())))
                .andDo(getDocumentHandler()
                        .document(
                                responseFields(
                                        fieldWithPath("id").description(""),
                                        fieldWithPath("firstName").description(""),
                                        fieldWithPath("lastName").description(""))));
    }

    @Test
    public void getEmployeeByIdNotExist() throws Exception {
        Employee employee = Employee.builder()
                .firstName("Ololo")
                .lastName("Trololo")
                .build();
        employeeService.save(employee);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(get("/employee/{id}", employee.getId() + 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.commonViolations",
                        containsInAnyOrder("Employee with id " + (employee.getId() + 1) + " doesn't exist")));
    }

    @Test
    public void getAllEmployeeDefault() throws Exception {
        Employee employee1 = Employee.builder()
                .firstName("Alex")
                .lastName("Pit")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("Bred")
                .lastName("Pit")
                .build();
        employeeService.save(employee1);
        employeeService.save(employee2);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(get("/employee")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andDo(getDocumentHandler()
                        .document(
                                responseFields(
                                        fieldWithPath(".content[].id").description(""),
                                        fieldWithPath(".content[].firstName").description(""),
                                        fieldWithPath(".content[].lastName").description(""),
                                        fieldWithPath(".totalElements").description(""),
                                        fieldWithPath(".totalPages").description(""),
                                        fieldWithPath(".number").description(""),
                                        fieldWithPath(".size").description(""))));
    }

    @Test
    public void getAllEmployeePaging() throws Exception {
        Employee employee1 = Employee.builder()
                .firstName("Alex")
                .lastName("Pit")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("Bred")
                .lastName("Pit")
                .build();
        employeeService.save(employee1);
        employeeService.save(employee2);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");

        getMockMvc().perform(get("/employee")
                .param("page", "2")
                .param("size", "1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.content.length()", is(1)))
                .andDo(getDocumentHandler()
                        .document(
                                responseFields(
                                        fieldWithPath(".content[].id").description(""),
                                        fieldWithPath(".content[].firstName").description(""),
                                        fieldWithPath(".content[].lastName").description(""),
                                        fieldWithPath(".totalElements").description(""),
                                        fieldWithPath(".totalPages").description(""),
                                        fieldWithPath(".number").description(""),
                                        fieldWithPath(".size").description(""))));
    }

}
