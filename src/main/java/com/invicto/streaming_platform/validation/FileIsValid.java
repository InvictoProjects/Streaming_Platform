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
@Constraint(validatedBy = FileValidator.class)
@Documented
public @interface FileIsValid {

    String message() default "Invalid file.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
