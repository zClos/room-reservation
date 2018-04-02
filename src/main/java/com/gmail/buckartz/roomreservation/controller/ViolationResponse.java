package com.gmail.buckartz.roomreservation.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class ViolationResponse {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, List<String>> fieldsViolations;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> commonViolations;

    public static ViolationHandlerBuilder builder() {
        return new ViolationHandlerBuilder();
    }

    public static class ViolationHandlerBuilder {
        private ViolationResponse violationResponse = new ViolationResponse();

        public ViolationHandlerBuilder fields(Map<String, List<String>> fieldsViolations) {
            violationResponse.fieldsViolations = fieldsViolations;
            return this;
        }

        public ViolationHandlerBuilder common(String common) {
            violationResponse.commonViolations = Collections.singletonList(common);
            return this;
        }

        public ViolationHandlerBuilder common(List<String> commonViolations) {
            violationResponse.commonViolations = commonViolations;
            return this;
        }

        public ViolationResponse build() {
            return violationResponse;
        }
    }
}

