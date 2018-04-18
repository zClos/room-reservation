package com.gmail.buckartz.roomreservation.authorization;

import com.gmail.buckartz.roomreservation.config.UnitTestConfiguration;
import com.gmail.buckartz.roomreservation.domain.employee.Authority;
import com.gmail.buckartz.roomreservation.domain.employee.Employee;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@UnitTestConfiguration
@RunWith(SpringRunner.class)
public class AppUserDetailServiceTest {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AppUserDetailsServiceImpl appUserDetailsService;

    @Test
    public void loadUserByUsername() {
        Employee employee = Employee.builder()
                .firstName("Nick")
                .lastName("Carter")
                .authority(Authority.builder()
                        .login("login")
                        .password("password")
                        .build())
                .build();
        employeeService.save(employee);

        UserDetails userDetails = appUserDetailsService.loadUserByUsername(employee.getAuthority().getLogin());

        assertEquals(employee.getAuthority().getLogin(), userDetails.getUsername());
        assertEquals(employee.getAuthority().getPassword(), userDetails.getPassword());
    }
}
