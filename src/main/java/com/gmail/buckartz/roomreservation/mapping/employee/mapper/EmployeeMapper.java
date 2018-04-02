package com.gmail.buckartz.roomreservation.mapping.employee.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeMapper {
    private String firstName;
    private String lastName;

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
