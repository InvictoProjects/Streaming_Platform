package com.invicto.streaming_platform.validation;

import com.invicto.streaming_platform.web.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserDto userDto = (UserDto) obj;
        return userDto.getPassword()
                .equals(userDto.getPasswordConfirmation());
    }
}
