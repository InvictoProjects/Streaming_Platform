package com.invicto.streaming_platform.persistence.repository;

import com.invicto.streaming_platform.persistence.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
