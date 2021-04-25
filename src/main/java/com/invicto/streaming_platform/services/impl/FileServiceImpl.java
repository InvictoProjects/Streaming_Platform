package com.invicto.streaming_platform.services.impl;

import com.invicto.streaming_platform.persistence.model.Video;
import com.invicto.streaming_platform.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    @Value("${upload.dir:${user.home}}")
    private String uploadDirectory;

    public void uploadFile(MultipartFile file, Video video) {
        if (file == null || video == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        try {
            Path directoryToSave = Paths.get(uploadDirectory + File.separator + StringUtils.cleanPath(video.getCreator().getEmail()));
            Files.createDirectories(directoryToSave);
            Path fileLocation = Paths.get(directoryToSave + File.separator + video.getId());
            Files.copy(file.getInputStream(), fileLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
