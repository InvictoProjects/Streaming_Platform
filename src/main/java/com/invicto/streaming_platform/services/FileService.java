package com.invicto.streaming_platform.services;

import com.invicto.streaming_platform.persistence.model.Video;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileService {
    void uploadFile(MultipartFile file, Video video);
    Path findByVideoId(long id);
}
