package com.gmail.buckartz.roomreservation.service.employee;

import com.gmail.buckartz.roomreservation.domain.employee.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    void save(Employee employee);

    Employee findOne(Long id);

    Page<Employee> findAll(int page, int size);

    boolean exists(Long id);
}
