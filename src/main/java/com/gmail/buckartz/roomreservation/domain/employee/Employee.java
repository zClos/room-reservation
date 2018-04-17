package com.gmail.buckartz.roomreservation.domain.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"reservations", "authority"})
public class Employee {
    @Id
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", allocationSize = 0)
    @GeneratedValue(generator = "employee_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "employee")
    @JsonBackReference
    private Set<Reservation> reservations = new HashSet<>();

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Authority authority;

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

        public EmployeeBuilder authority(Authority authority) {
            employee.setAuthority(authority);
            authority.setEmployee(employee);
            return this;
        }

        public Employee build() {
            return employee;
        }
    }
}
