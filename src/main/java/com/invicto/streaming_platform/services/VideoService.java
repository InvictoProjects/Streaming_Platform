package com.invicto.streaming_platform.services;

import com.invicto.streaming_platform.persistence.model.Video;
import java.util.List;
import java.util.Optional;

public interface VideoService {
    Video createVideo(Video video);
    void deleteVideo(Video video);
    Video updateVideo(Video video);
    List<Video> findAll();
    Optional<Video> findById(Long id);
    List<Video> findByTitle(String title);
    List<Video> getPageSortedByViewsCount(int pageNumber, int pageSize);
}
