package com.invicto.streaming_platform.validation;

import com.invicto.streaming_platform.web.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class BirthValidator implements ConstraintValidator<BirthIsValid, Object> {

    @Override
    public void initialize(final BirthIsValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserDto userDto = (UserDto) obj;
        boolean result = true;
        LocalDate dateOfBirth = userDto.getDateOfBirth();
        if (dateOfBirth != null) {
            ChronoLocalDate currentDate = LocalDate.now();
            result = currentDate.isAfter(dateOfBirth);
        }
        return result;
    }
}
