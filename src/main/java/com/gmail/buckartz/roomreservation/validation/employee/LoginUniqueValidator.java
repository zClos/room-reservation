package com.gmail.buckartz.roomreservation.validation.employee;

import com.gmail.buckartz.roomreservation.dao.authorization.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class LoginUniqueValidator implements ConstraintValidator<LoginUniqueConstraint, String> {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void initialize(LoginUniqueConstraint constraintAnnotation) {
    }

    @Override
    @Transactional
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (!value.isEmpty()) && (!authorityRepository.existsByLogin(value));
    }
}
