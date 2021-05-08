package com.invicto.streaming_platform.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = BirthValidator.class)
@Documented
public @interface BirthIsValid {

    String message() default "Invalid date of birth.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
