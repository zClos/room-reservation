package com.gmail.buckartz.roomreservation.mapping.employee.impl;

import com.gmail.buckartz.roomreservation.domain.employee.Employee;
import com.gmail.buckartz.roomreservation.mapping.employee.EmployeeDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDeserializeMappingImpl implements EmployeeDeserializeMapping {
    @Override
    public Employee toObject(EmployeeMapper from) {
        return Employee.builder()
                .firstName(from.getLastName())
                .lastName(from.getLastName())
                .build();
    }
}
