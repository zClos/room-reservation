package com.gmail.buckartz.roomreservation.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@EqualsAndHashCode(exclude = "reservations")
public class Employee {
    @Id
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", allocationSize = 1)
    @GeneratedValue(generator = "employee_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "employee")
    @JsonBackReference
    private Set<Reservation> reservations = new HashSet<>();

    public static EmployeeBuilder builder() {
        return new EmployeeBuilder();
    }

    public static class EmployeeBuilder {
        private Employee employee = new Employee();

        private EmployeeBuilder() {
        }

        public EmployeeBuilder firstName(String firstName) {
            employee.setFirstName(firstName);
            return this;
        }

        public EmployeeBuilder lastName(String lastName) {
            employee.setLastName(lastName);
            return this;
        }

        public Employee build() {
            return employee;
        }
    }
}
