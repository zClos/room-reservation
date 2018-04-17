package com.gmail.buckartz.roomreservation.service.reservation.impl;

import com.gmail.buckartz.roomreservation.dao.ReservationRepository;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.service.reservation.ReservationService;
import com.gmail.buckartz.roomreservation.service.reservation.filter.ReservationSearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ReservationEqualSpecification.employeeId;
import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ReservationSoftBetweenSpecification.reservedFromBetween;
import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ReservationSoftBetweenSpecification.reservedToBetween;
import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ResevationCombinedOperatorsSpecifications.andSpecifications;
import static com.gmail.buckartz.roomreservation.dao.secifications.reservation.ResevationCombinedOperatorsSpecifications.orSpecifications;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public boolean isReservedRoomForPeriod(Long id, Timestamp from, Timestamp to) {
        return (reservationRepository.isReservedRoomForPeriod(id, from, to) == 1);
    }

    @Override
    public List<Reservation> findAllByFilter(ReservationSearchFilters searchFilters) {
        return reservationRepository.findAll(
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

