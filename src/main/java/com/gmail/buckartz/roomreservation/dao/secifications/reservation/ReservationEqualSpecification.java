package com.gmail.buckartz.roomreservation.dao.secifications.reservation;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.domain.Reservation_;
import com.gmail.buckartz.roomreservation.domain.employee.Employee_;
import org.springframework.data.jpa.domain.Specification;

public class ReservationEqualSpecification {

    public static Specification<Reservation> employeeId(Long id) {
        return (root, query, cb) -> {
            if (id != null) {
                return cb.equal(root.get(Reservation_.employee).get(Employee_.id), id);
            }
            return null;
        };
    }
}
