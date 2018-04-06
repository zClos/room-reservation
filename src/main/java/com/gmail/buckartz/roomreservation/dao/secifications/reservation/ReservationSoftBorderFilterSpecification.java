package com.gmail.buckartz.roomreservation.dao.secifications.reservation;

import com.gmail.buckartz.roomreservation.domain.Employee_;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.domain.Reservation_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReservationSoftBorderFilterSpecification {

    public static Specification<Reservation> employeeId(Long id) {
        return (root, query, cb) -> {
            if (id != null) {
                return cb.equal(root.get(Reservation_.employee).get(Employee_.id), id);
            }
            return null;
        };
    }

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

    public static Specification<Reservation> andSpecifications(Specification... specifications) {
        List<Predicate> predicateList = new LinkedList<>();
        return (root, query, cb) -> {
            Arrays.stream(specifications).forEach(sec -> {
                Predicate predicate = sec.toPredicate(root, query, cb);
                if (predicate != null) predicateList.add(predicate);
            });
            if (predicateList.size() == 0) return null;
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };
    }

    public static Specification<Reservation> orSpecifications(Specification... specifications) {
        List<Predicate> predicateList = new LinkedList<>();
        return (root, query, cb) -> {
            Arrays.stream(specifications).forEach(sec -> {
                Predicate predicate = sec.toPredicate(root, query, cb);
                if (predicate != null) predicateList.add(predicate);
            });
            if (predicateList.size() == 0) return null;
            return cb.or(predicateList.toArray(new Predicate[predicateList.size()]));
        };
    }
}
