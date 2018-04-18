package com.gmail.buckartz.roomreservation.controller.employee;

import com.gmail.buckartz.roomreservation.config.ControllerTestContext;
import com.gmail.buckartz.roomreservation.config.IntegrationWebTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.employee.Employee;
import com.gmail.buckartz.roomreservation.mapping.employee.EmployeeDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeDeserializeMapper;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final static String LOGIN = "my_login";
    private final static String PASSWORD = "password";
    private Employee employee;

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
        employee = employeeDeserializeMapping.toObject(mapper);
        employeeService.save(employee);
    }

    @Test
    public void getEmployeeById() throws Exception {
        getMockMvc().perform(get("/employee/{id}", employee.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD)))
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
        getMockMvc().perform(get("/employee/{id}", employee.getId() + 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.commonViolations",
                        containsInAnyOrder("Employee with id " + (employee.getId() + 1) + " doesn't exist")));
    }

    @Test
    public void getAllEmployeeDefault() throws Exception {
        Employee employee = Employee.builder()
                .firstName("Alex")
                .lastName("Pit")
                .build();
        employeeService.save(employee);

        getMockMvc().perform(get("/employee")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD)))
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
        Employee employee = Employee.builder()
                .firstName("Alex")
                .lastName("Pit")
                .build();
        employeeService.save(employee);

        getMockMvc().perform(get("/employee")
                .param("page", "2")
                .param("size", "1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(authHeaders(LOGIN, PASSWORD)))
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
