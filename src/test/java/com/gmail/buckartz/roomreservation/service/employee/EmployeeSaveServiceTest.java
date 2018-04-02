package com.gmail.buckartz.roomreservation.service.employee;

import com.gmail.buckartz.roomreservation.config.UnitTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@UnitTestConfiguration
@RunWith(SpringRunner.class)
public class EmployeeSaveServiceTest {
    @Autowired
    private EmployeeSaveService employeeSaveService;

    @Test
    public void saveEmployee() {
        Employee employee = Employee.builder()
                .firstName("Donald")
                .lastName("Tramp")
                .build();

        employeeSaveService.save(employee);
        assertNotNull(employee.getId());
    }
}
