package com.gmail.buckartz.roomreservation.mapping.employee.mapper;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

@Getter
@Setter
@GroupSequence({EmployeeMapper.class, EmployeeMapper.SizeEmployeeValuesGroup.class})
public class EmployeeMapper {
    @NotEmpty(message = "{NotEmpty.employeeMapper.firstName}")
    @Size(min = 2, max = 16, message = "{Size.employeeMapper.firstName}", groups = SizeEmployeeValuesGroup.class)
    private String firstName;
    @NotEmpty(message = "{NotEmpty.employeeMapper.lastName}")
    @Size(min = 2, max = 16, message = "{Size.employeeMapper.lastName}", groups = SizeEmployeeValuesGroup.class)
    private String lastName;

    public interface SizeEmployeeValuesGroup {
    }

    public static EmployeeMapperBuilder builder() {
        return new EmployeeMapperBuilder();
    }

    public static class EmployeeMapperBuilder {
        private EmployeeMapper mapper = new EmployeeMapper();

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

        public EmployeeMapper build() {
            return mapper;
        }
    }
}
