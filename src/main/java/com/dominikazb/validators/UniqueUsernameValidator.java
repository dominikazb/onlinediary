package com.dominikazb.validators;

import com.dominikazb.beans.User;
import com.dominikazb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import static org.springframework.data.util.Optionals.ifPresentOrElse;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {



    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if(userRepository == null) {
            return true;
        }

        return userRepository.findUserNotOptionalByUsername(username) == null;
    }

}
