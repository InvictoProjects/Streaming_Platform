package com.invicto.streaming_platform.services.impl;

import com.invicto.streaming_platform.persistence.model.Video;
import com.invicto.streaming_platform.persistence.repository.VideoRepository;
import com.invicto.streaming_platform.services.VideoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }


    @Override
    public Video createVideo(Video video) {
        if (video.getId() != null && videoRepository.existsById(video.getId())) {
            throw new EntityExistsException("Video with id " + video.getId() + " is already exists");
        }
        return videoRepository.save(video);
    }

    @Override
    public void deleteVideo(Video video) {
        if (video.getId() == null) {
            throw new IllegalArgumentException("Video id can not be null");
        } else if (!videoRepository.existsById(video.getId())) {
            throw new EntityNotFoundException("Video with id " + video.getId() + " does not exist");
        }
        videoRepository.delete(video);
    }

    @Override
    public Video updateVideo(Video video) {
        if (video.getId() == null) {
            throw new IllegalArgumentException("Video id can not be null");
        } else if (!videoRepository.existsById(video.getId())) {
            throw new EntityNotFoundException("Video with id " + video.getId() + " does not exist");
        }
        return videoRepository.save(video);
    }

    @Override
    public List<Video> findAll() {
        List<Video> videos = new ArrayList<>();
        videoRepository.findAll().forEach(videos::add);
        return videos;
    }

    @Override
    public Optional<Video> findById(Long id) {
        return videoRepository.findById(id);
    }

    @Override
    public List<Video> findByTitle(String title) {
        List<Video> videos = new ArrayList<>();
        videoRepository.findByTitle(title).forEach(videos::add);
        return videos;
    }

    @Override
    public List<Video> getPageSortedByViewsCount(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("viewsCount").descending());
        Page<Video> videosPage = videoRepository.findAll(pageable);
        return videosPage.toList();
    }
}
