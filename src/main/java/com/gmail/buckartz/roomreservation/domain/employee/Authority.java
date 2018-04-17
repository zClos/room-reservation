package com.gmail.buckartz.roomreservation.domain.employee;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "authority")
@Getter
@Setter
public class Authority {
    @Id
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    @JsonManagedReference
    private Employee employee;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    public static AuthorityBuilder builder() {
        return new AuthorityBuilder();
    }

    public static class AuthorityBuilder {
        private Authority authority = new Authority();

        public AuthorityBuilder employee(Employee employee) {
            authority.setEmployee(employee);
            employee.setAuthority(authority);
            return this;
        }

        public AuthorityBuilder login(String login) {
            authority.setLogin(login);
            return this;
        }

        public AuthorityBuilder password(String password) {
            authority.setPassword(password);
            return this;
        }

        public Authority build() {
            return authority;
        }
    }
}
