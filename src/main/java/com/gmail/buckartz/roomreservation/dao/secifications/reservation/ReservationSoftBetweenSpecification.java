package com.gmail.buckartz.roomreservation.dao.secifications.reservation;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.domain.Reservation_;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;

public class ReservationSoftBetweenSpecification {
    public static Specification<Reservation> reservedFromBetween(Timestamp from, Timestamp to) {
        return (root, query, cb) -> {
            if (from != null && to != null) {
                return cb.between(root.get(Reservation_.reservedFrom), from, to);
            } else if (from != null) {
                return cb.greaterThanOrEqualTo(root.get(Reservation_.reservedTo), from);
            }
            return null;
        };
    }

    public static Specification<Reservation> reservedToBetween(Timestamp from, Timestamp to) {
        return (root, query, cb) -> {
            if (from != null && to != null) {
                return cb.between(root.get(Reservation_.reservedTo), from, to);
            } else if (to != null) {
                return cb.lessThanOrEqualTo(root.get(Reservation_.reservedFrom), to);
            }
            return null;
        };
    }
}
