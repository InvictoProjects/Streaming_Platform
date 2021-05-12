package com.invicto.streaming_platform.services.impl;

import com.invicto.streaming_platform.persistence.model.Video;
import com.invicto.streaming_platform.services.FileService;
import com.invicto.streaming_platform.services.VideoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Service
public class FileServiceImpl implements FileService {

    @Value("${video.source:${user.home}}")
    private String uploadDirectory;
    private final VideoService videoService;

    public FileServiceImpl(VideoService videoService) {
        this.videoService = videoService;
    }


    public void uploadFile(MultipartFile file, Video video) {
        if (file == null || video == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        try {
            Path directoryToSave = Paths.get(uploadDirectory + File.separator + StringUtils.cleanPath(video.getCreator().getEmail()));
            Files.createDirectories(directoryToSave);
            Path fileLocation = Paths.get(directoryToSave + File.separator + video.getId().toString() + getFileExtension(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), fileLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Path findPathByVideoId(long id) {
        Video video = videoService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Arguments cannot be null"));;
        String creatorId = String.valueOf(video.getCreator().getId());
        String dirPath = uploadDirectory+File.separator+creatorId;
        String filePath = dirPath+File.separator+video.getId();
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("regex:"+filePath+"\\.mp4|webm|ogg");
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IllegalArgumentException("There is no videos of that user");
        }
        for (File file : files) {
            Path path = file.toPath();
            if (matcher.matches(path)) {
                return path;
            }
        }
        return null;
    }

    private String getFileExtension(String fileName) {
        StringBuilder builder = new StringBuilder(fileName);
        return builder.substring(builder.indexOf("."));
    }
}
