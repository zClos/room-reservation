package com.gmail.buckartz.roomreservation.mapping.employee.impl;

import com.gmail.buckartz.roomreservation.domain.employee.Authority;
import com.gmail.buckartz.roomreservation.domain.employee.Employee;
import com.gmail.buckartz.roomreservation.mapping.employee.EmployeeDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeDeserializeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDeserializeMappingImpl implements EmployeeDeserializeMapping {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Employee toObject(EmployeeDeserializeMapper from) {
        return Employee.builder()
                .firstName(from.getLastName())
                .lastName(from.getLastName())
                .authority(Authority.builder()
                        .login(from.getLogin())
                        .password(bCryptPasswordEncoder.encode(from.getPassword()))
                        .build())
                .build();
    }
}
