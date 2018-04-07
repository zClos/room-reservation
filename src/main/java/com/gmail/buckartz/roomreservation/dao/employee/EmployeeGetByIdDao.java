package com.gmail.buckartz.roomreservation.dao.employee;

import com.gmail.buckartz.roomreservation.domain.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeGetByIdDao extends org.springframework.data.repository.Repository<Employee, Long> {
    Employee findById(Long id);
}
