package com.invicto.streaming_platform.services;

import com.invicto.streaming_platform.persistence.model.Video;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface VideoService {
    Video createVideo(Video video);
    void deleteVideo(Video video);
    Video updateVideo(Video video);
    List<Video> findAll();
    Optional<Video> findById(Long id);
    List<Video> findByTitle(String title);
}
