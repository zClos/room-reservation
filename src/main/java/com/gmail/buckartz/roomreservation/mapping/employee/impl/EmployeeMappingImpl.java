package com.gmail.buckartz.roomreservation.mapping.employee.impl;

import com.gmail.buckartz.roomreservation.domain.Employee;
import com.gmail.buckartz.roomreservation.mapping.employee.EmployeeMapping;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMappingImpl implements EmployeeMapping {
    @Override
    public Employee toObject(EmployeeMapper from) {
        return Employee.builder()
                .firstName(from.getLastName())
                .lastName(from.getLastName())
                .build();
    }
}
