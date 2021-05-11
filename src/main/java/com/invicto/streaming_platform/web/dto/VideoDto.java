package com.invicto.streaming_platform.web.dto;

import com.invicto.streaming_platform.validation.FileIsValid;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@FileIsValid
public class VideoDto {
    @NotEmpty(message = "Title is required.")
    private String title;

    @NotEmpty(message = "Description is required.")
    private String description;

    private MultipartFile source;
    private MultipartFile banner;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getSource() {
        return source;
    }

    public void setSource(MultipartFile source) {
        this.source = source;
    }

    public MultipartFile getBanner() {
        return banner;
    }

    public void setBanner(MultipartFile banner) {
        this.banner = banner;
    }
}
