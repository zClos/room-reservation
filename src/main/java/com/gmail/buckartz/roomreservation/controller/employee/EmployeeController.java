package com.gmail.buckartz.roomreservation.controller.employee;

import com.gmail.buckartz.roomreservation.controller.DefaultHeaderValues;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.mapping.employee.EmployeeMapping;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeMapper;
import com.gmail.buckartz.roomreservation.mapping.reservation.ReservationMapping;
import com.gmail.buckartz.roomreservation.mapping.reservation.ReservationToJsonListMapping;
import com.gmail.buckartz.roomreservation.mapping.reservation.mapper.ReservationMapper;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeSaveService;
import com.gmail.buckartz.roomreservation.service.reservation.ReservationGetAllByFilterService;
import com.gmail.buckartz.roomreservation.service.reservation.ReservationSaveService;
import com.gmail.buckartz.roomreservation.service.reservation.filter.ReservationSearchFilters;
import com.gmail.buckartz.roomreservation.validation.employee.EmployeeIdExistenceConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@DefaultHeaderValues
@RequestMapping("/employee")
@Validated
public class EmployeeController {
    @Autowired
    private EmployeeSaveService employeeSaveService;

    @Autowired
    private EmployeeMapping employeeMapping;

    @Autowired
    private ReservationSaveService reservationSaveService;

    @Autowired
    private ReservationMapping reservationMapping;

    @Autowired
    private ReservationToJsonListMapping reservationToJsonListMapping;

    @Autowired
    private ReservationGetAllByFilterService getAllByFilterService;

    @PostMapping
    public ResponseEntity saveEmployee(@Valid @RequestBody EmployeeMapper employeeMapper) {
        employeeSaveService.save(employeeMapping.toObject(employeeMapper));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/{id}/reservation")
    public ResponseEntity saveEmployeeRoomReservation(@EmployeeIdExistenceConstraint @PathVariable("id") Long id, @Valid @RequestBody ReservationMapper mapper) {
        ReservationMapper reservationMapper = ReservationMapper.builder(mapper)
                .employeeId(id)
                .builder();
        reservationSaveService.save(reservationMapping.toObject(reservationMapper));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/reservation")
    public ResponseEntity<List<Reservation>> getAllEmployeeRoomReservations(@EmployeeIdExistenceConstraint @PathVariable("id") Long id,
                                                                            @RequestParam("from") Optional<String> from,
                                                                            @RequestParam("to") Optional<String> to,
                                                                            @RequestParam("order") Optional<String> order) {
        ReservationSearchFilters filters = ReservationSearchFilters.builder()
                .id(id)
                .from(from.orElse(null))
                .to(to.orElse(null))
                .order(order.orElse(null))
                .build();
        List<Reservation> list = getAllByFilterService.getAllByFilter(filters);
        return new ResponseEntity(reservationToJsonListMapping.toJson(list), HttpStatus.OK);
    }
}
