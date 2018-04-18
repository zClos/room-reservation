package com.gmail.buckartz.roomreservation.mapping.employee.mapper;

import com.gmail.buckartz.roomreservation.validation.employee.LoginUniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

@Getter
@Setter
@GroupSequence({EmployeeDeserializeMapper.class,
        EmployeeDeserializeMapper.SizeEmployeeValuesGroup.class,
        EmployeeDeserializeMapper.LoginExistenceGroup.class})
public class EmployeeDeserializeMapper {
    @NotEmpty(message = "{NotEmpty.employeeDeserializeMapper.firstName}")
    @Size(min = 2, max = 16, message = "{Size.employeeDeserializeMapper.firstName}", groups = SizeEmployeeValuesGroup.class)
    private String firstName;
    @NotEmpty(message = "{NotEmpty.employeeDeserializeMapper.lastName}")
    @Size(min = 2, max = 16, message = "{Size.employeeDeserializeMapper.lastName}", groups = SizeEmployeeValuesGroup.class)
    private String lastName;
    @NotEmpty(message = "{NotEmpty.employeeDeserializeMapper.login}")
    @Size(min = 6, max = 12, message = "{Size.employeeDeserializeMapper.login}", groups = SizeEmployeeValuesGroup.class)
    @LoginUniqueConstraint(groups = LoginExistenceGroup.class)
    private String login;
    @NotEmpty(message = "{NotEmpty.employeeDeserializeMapper.password}")
    @Size(min = 6, max = 12, message = "{Size.employeeDeserializeMapper.password}", groups = SizeEmployeeValuesGroup.class)
    private String password;

    public interface SizeEmployeeValuesGroup {
    }

    public interface LoginExistenceGroup {
    }

    public static EmployeeMapperBuilder builder() {
        return new EmployeeMapperBuilder();
    }

    public static class EmployeeMapperBuilder {
        private EmployeeDeserializeMapper mapper = new EmployeeDeserializeMapper();

        private EmployeeMapperBuilder() {
        }

        public EmployeeMapperBuilder firstName(String firstName) {
            mapper.setFirstName(firstName);
            return this;
        }

        public EmployeeMapperBuilder lastName(String lastName) {
            mapper.setLastName(lastName);
            return this;
        }

        public EmployeeMapperBuilder login(String login) {
            mapper.setLogin(login);
            return this;
        }

        public EmployeeMapperBuilder password(String password) {
            mapper.setPassword(password);
            return this;
        }

        public EmployeeDeserializeMapper build() {
            return mapper;
        }
    }
}
