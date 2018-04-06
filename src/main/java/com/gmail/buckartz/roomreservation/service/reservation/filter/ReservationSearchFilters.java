package com.gmail.buckartz.roomreservation.service.reservation.filter;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.parse;

@Getter
public class ReservationSearchFilters {
    private Long id;
    private Timestamp from;
    private Timestamp to;
    private String order;

    public static ReservationFiltersBuilder builder() {
        return new ReservationFiltersBuilder();
    }

    public static class ReservationFiltersBuilder {
        private ReservationSearchFilters filters = new ReservationSearchFilters();

        public ReservationFiltersBuilder id(Long id) {
            filters.id = id;
            return this;
        }

        public ReservationFiltersBuilder from(String from) {
            filters.from = (from != null) ? Timestamp.valueOf(parse(from, DateTimeFormatter.ISO_LOCAL_DATE_TIME)) : null;
            return this;
        }

        public ReservationFiltersBuilder to(String to) {
            filters.to = (to != null) ? Timestamp.valueOf(parse(to, DateTimeFormatter.ISO_LOCAL_DATE_TIME)) : null;
            return this;
        }

        public ReservationFiltersBuilder order(String order) {
            filters.order = order;
            return this;
        }

        public ReservationSearchFilters build() {
            return filters;
        }
    }
}
