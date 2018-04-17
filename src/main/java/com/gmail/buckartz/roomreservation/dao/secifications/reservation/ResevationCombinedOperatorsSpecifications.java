package com.gmail.buckartz.roomreservation.dao.secifications.reservation;

import com.gmail.buckartz.roomreservation.domain.Reservation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ResevationCombinedOperatorsSpecifications {
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
