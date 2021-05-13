package com.invicto.streaming_platform.services;

import com.invicto.streaming_platform.persistence.model.Comment;
import com.invicto.streaming_platform.persistence.model.Video;

import java.util.List;

public interface CommentService {
    void addCommentToVideo(Comment comment, Video video);
    List<Comment> getCommentsToVideo(Video video);
}
