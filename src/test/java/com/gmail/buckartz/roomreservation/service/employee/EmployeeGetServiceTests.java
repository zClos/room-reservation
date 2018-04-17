package com.gmail.buckartz.roomreservation.service.employee;

import com.gmail.buckartz.roomreservation.config.UnitTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@UnitTestConfiguration
@RunWith(SpringRunner.class)
public class EmployeeGetServiceTests {
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void getEmployeeById() {
        Employee employee = Employee.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
        employeeService.save(employee);

        assertEquals(employee, employeeService.findOne(employee.getId()));
    }

    @Test
    public void getAllEmployee() {
        Employee employee1 = Employee.builder()
                .firstName("Donald")
                .lastName("Tramp")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("Vasya")
                .lastName("Pupkin")
                .build();
        employeeService.save(employee1);
        employeeService.save(employee2);

        Page<Employee> employees = employeeService.findAll(0, 15);
        assertEquals(2, employees.getContent().size());
    }
}
