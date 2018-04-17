package com.gmail.buckartz.roomreservation.controller.reservation;

import com.gmail.buckartz.roomreservation.controller.DefaultHeaderValues;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.mapping.reservation.ReservationDeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.reservation.ReservationSerializeListMapping;
import com.gmail.buckartz.roomreservation.mapping.reservation.mapper.ReservationDeserializeMapper;
import com.gmail.buckartz.roomreservation.mapping.reservation.mapper.ReservationSerializeMapper;
import com.gmail.buckartz.roomreservation.service.reservation.ReservationService;
import com.gmail.buckartz.roomreservation.service.reservation.filter.ReservationSearchFilters;
import com.gmail.buckartz.roomreservation.validation.employee.EmployeeIdExistenceConstraint;
import com.gmail.buckartz.roomreservation.validation.reservations.DateFormatConstraint;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Optional;

@RestController
@DefaultHeaderValues
@RequestMapping("/employee")
@Validated
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationDeserializeMapping reservationMapping;

    @Autowired
    private ReservationSerializeListMapping reservationSerializeListMapping;

    @PostMapping("/{id}/reservation")
    public ResponseEntity saveEmployeeRoomReservation(@EmployeeIdExistenceConstraint @PathVariable("id") Long id, @Valid @RequestBody ReservationDeserializeMapper mapper) {
        ReservationDeserializeMapper reservationDeserializeMapper = ReservationDeserializeMapper.builder(mapper)
                .employeeId(id)
                .builder();
        reservationService.save(reservationMapping.toObject(reservationDeserializeMapper));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/reservation")
    public ResponseEntity<List<ReservationSerializeMapper>> findAllEmployeeRoomReservations(@EmployeeIdExistenceConstraint @PathVariable("id") Long id,
                                                                                            @DateFormatConstraint(required = false) @RequestParam("from") Optional<String> from,
                                                                                            @DateFormatConstraint(required = false) @RequestParam("to") Optional<String> to,
                                                                                            @RequestParam("order") Optional<String> order) {
        ReservationSearchFilters filters = ReservationSearchFilters.builder()
                .id(id)
                .from(from.orElse(null))
                .to(to.orElse(null))
                .order(order.orElse(null))
                .build();
        List<Reservation> list = reservationService.findAllByFilter(filters);
        return (list.isEmpty()) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(reservationSerializeListMapping.toJson(list), HttpStatus.OK);
    }
}
