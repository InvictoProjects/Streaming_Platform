package com.invicto.streaming_platform.persistence.repository;

import com.invicto.streaming_platform.persistence.model.Video;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends CrudRepository<Video, Long> {
    Iterable<Video> findByTitle(String title);
}
