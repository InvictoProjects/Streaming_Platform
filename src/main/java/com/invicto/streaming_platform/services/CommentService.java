package com.invicto.streaming_platform.services;

import com.invicto.streaming_platform.persistence.model.Comment;
import com.invicto.streaming_platform.persistence.model.Video;

public interface CommentService {
    void addCommentToVideo(Comment comment, Video video);
}
