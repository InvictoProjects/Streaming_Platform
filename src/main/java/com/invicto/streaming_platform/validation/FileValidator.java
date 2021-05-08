package com.invicto.streaming_platform.validation;

import com.invicto.streaming_platform.web.dto.VideoDto;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<FileIsValid, Object> {

    @Override
    public void initialize(final FileIsValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final VideoDto video = (VideoDto) obj;
        final MultipartFile file = video.getSource();
        return !file.isEmpty();
    }
}
