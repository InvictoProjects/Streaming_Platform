package com.invicto.streaming_platform.services;

import com.invicto.streaming_platform.persistence.model.Video;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public void uploadFile(MultipartFile file, Video video);
}
