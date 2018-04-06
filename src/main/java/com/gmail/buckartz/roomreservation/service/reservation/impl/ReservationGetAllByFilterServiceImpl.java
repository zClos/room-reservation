package com.gmail.buckartz.roomreservation.service.reservation.impl;

import com.gmail.buckartz.roomreservation.dao.reservation.ReservationGettingFilteredDao;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.service.reservation.ReservationGetAllByFilterService;
import com.gmail.buckartz.roomreservation.service.reservation.filter.ReservationSearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ReservationSoftBorderFilterSpecification.andSpecifications;
import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ReservationSoftBorderFilterSpecification.employeeId;
import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ReservationSoftBorderFilterSpecification.orSpecifications;
import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ReservationSoftBorderFilterSpecification.reservedFromBetween;
import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ReservationSoftBorderFilterSpecification.reservedToBetween;

@Service
public class ReservationGetAllByFilterServiceImpl implements ReservationGetAllByFilterService {
    @Autowired
    private ReservationGettingFilteredDao gettingFilteredDao;

    @Override
    public List<Reservation> getAllByFilter(ReservationSearchFilters searchFilters) {
        return gettingFilteredDao.findAll(
                andSpecifications(
                        employeeId(searchFilters.getId()),
                        orSpecifications(
                                reservedFromBetween(searchFilters.getFrom(), searchFilters.getTo()),
                                reservedToBetween(searchFilters.getFrom(), searchFilters.getTo()))),
                ((searchFilters.getOrder() != null) && searchFilters.getOrder().trim().toLowerCase().equals("asc")) ?
                        new Sort(Sort.Direction.ASC, "reservedFrom") :
                        ((searchFilters.getOrder() != null) && searchFilters.getOrder().trim().toLowerCase().equals("desc")) ?
                                new Sort(Sort.Direction.DESC, "reservedFrom") :
                                new Sort(Sort.DEFAULT_DIRECTION, "id"));
    }
}
