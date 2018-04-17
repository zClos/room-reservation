package com.gmail.buckartz.roomreservation.controller.employee;

import com.gmail.buckartz.roomreservation.controller.DefaultHeaderValues;
import com.gmail.buckartz.roomreservation.domain.Employee;
import com.gmail.buckartz.roomreservation.mapping.content_page.PageSerializeMapping;
import com.gmail.buckartz.roomreservation.mapping.content_page.mapper.PageSerializeMapper;
import com.gmail.buckartz.roomreservation.mapping.employee.EmployeeDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeMapper;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeService;
import com.gmail.buckartz.roomreservation.validation.employee.EmployeeIdExistenceConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@DefaultHeaderValues
@RequestMapping("/employee")
@Validated
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeDeserializeMapping employeeMapping;

    @Autowired
    private PageSerializeMapping<Employee> pageSerializeMapping;

    @PostMapping
    public ResponseEntity saveEmployee(@Valid @RequestBody EmployeeMapper employeeMapper) {
        employeeService.save(employeeMapping.toObject(employeeMapper));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findEmployeeById(@EmployeeIdExistenceConstraint @PathVariable("id") Long id) {
        Employee employees = employeeService.findOne(id);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageSerializeMapper> findAllEmployees(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                                                @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Page<Employee> employees = employeeService.findAll(
                page.filter(value -> value > 0).map(value -> value - 1).orElse(0),
                size.filter(value -> value > 0).orElse(15));
        return (employees.getContent().isEmpty()) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(pageSerializeMapping.toJson(employees), HttpStatus.OK);
    }
}
