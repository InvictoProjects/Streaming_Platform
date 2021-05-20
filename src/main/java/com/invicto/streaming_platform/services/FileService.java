package com.invicto.streaming_platform.services;

import com.invicto.streaming_platform.persistence.model.Video;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

public interface FileService {
    void uploadFile(MultipartFile file, Video video);
    Path findPathByVideoId(long id);
    Optional<Path> findThumbnailPathById(long id);
}
