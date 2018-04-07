package com.gmail.buckartz.roomreservation.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reservation", schema = "public")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"room", "employee"})
public class Reservation {

    @Id
    @SequenceGenerator(name = "reservation_seq", sequenceName = "reservation_seq", schema = "public", allocationSize = 0)
    @GeneratedValue(generator = "reservation_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    @JsonManagedReference
    private Room room;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    @JsonManagedReference
    private Employee employee;

    @Column(name = "reserved_from")
    private Timestamp reservedFrom;

    @Column(name = "reserved_to")
    private Timestamp reservedTo;

    @Column(name = "reason")
    private String reason;

    public static ReservationBuilder builder() {
        return new ReservationBuilder();
    }

    public static class ReservationBuilder {
        private Reservation reservation = new Reservation();

        private ReservationBuilder() {
        }

        public ReservationBuilder room(Room room) {
            reservation.setRoom(room);
            room.getReservations().add(reservation);
            return this;
        }

        public ReservationBuilder employee(Employee employee) {
            reservation.setEmployee(employee);
            employee.getReservations().add(reservation);
            return this;
        }

        public ReservationBuilder reservedFrom(Timestamp dateTimeFrom) {
            reservation.setReservedFrom(dateTimeFrom);
            return this;
        }

        public ReservationBuilder reservedTo(Timestamp dateTimeTo) {
            reservation.setReservedTo(dateTimeTo);
            return this;
        }

        public ReservationBuilder reason(String reason) {
            reservation.setReason(reason);
            return this;
        }

        public Reservation build() {
            return reservation;
        }
    }
}
