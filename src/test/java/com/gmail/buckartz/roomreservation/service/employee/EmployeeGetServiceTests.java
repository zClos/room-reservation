package com.gmail.buckartz.roomreservation.service.employee;

import com.gmail.buckartz.roomreservation.config.UnitTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@UnitTestConfiguration
@RunWith(SpringRunner.class)
public class EmployeeGetServiceTests {
    @Autowired
    private EmployeeSaveService employeeSaveService;

    @Autowired
    private EmployeeGetByIdService employeeGetByIdService;

    @Autowired
    private EmployeeGetAllService getAllService;

    @Test
    public void getEmployeeById() {
        Employee employee = Employee.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
        employeeSaveService.save(employee);

        assertEquals(employee, employeeGetByIdService.findById(employee.getId()));
    }

    @Test
    public void getEmployeeAll() {
        Employee employee1 = Employee.builder()
                .firstName("Donald")
                .lastName("Tramp")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("Vasya")
                .lastName("Pupkin")
                .build();
        employeeSaveService.save(employee1);
        employeeSaveService.save(employee2);

        assertEquals(2, getAllService.findAll().size());
    }
}
