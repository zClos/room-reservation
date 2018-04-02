package com.gmail.buckartz.roomreservation.controller.employee;

import com.gmail.buckartz.roomreservation.controller.DefaultHeaderValues;
import com.gmail.buckartz.roomreservation.mapping.employee.EmployeeMapping;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeMapper;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@DefaultHeaderValues
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeSaveService employeeSaveService;

    @Autowired
    private EmployeeMapping employeeMapping;

    @PostMapping
    public ResponseEntity saveEmployee(@Valid @RequestBody EmployeeMapper employeeMapper) {
        employeeSaveService.save(employeeMapping.toObject(employeeMapper));
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
