package com.invicto.streaming_platform.persistence.repository;

import com.invicto.streaming_platform.persistence.model.Video;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends CrudRepository<Video, Long> {
    Optional<Video> findByTitle(String title);
}
