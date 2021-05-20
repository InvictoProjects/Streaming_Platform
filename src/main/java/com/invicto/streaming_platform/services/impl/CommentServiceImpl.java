package com.invicto.streaming_platform.services.impl;

import com.invicto.streaming_platform.persistence.model.Comment;
import com.invicto.streaming_platform.persistence.model.Video;
import com.invicto.streaming_platform.persistence.repository.CommentRepository;
import com.invicto.streaming_platform.persistence.repository.VideoRepository;
import com.invicto.streaming_platform.services.CommentService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;

    public CommentServiceImpl(CommentRepository commentRepository, VideoRepository videoRepository) {
        this.commentRepository = commentRepository;
        this.videoRepository = videoRepository;
    }

    @Override
    public void addCommentToVideo(Comment comment, Video video) {
        if (comment.getText() == null || comment.getText().strip().equals("")) {
            throw new IllegalArgumentException("There is no text of comment");
        }
        commentRepository.save(comment);
        video.getComments().add(comment);
        videoRepository.save(video);
    }

    @Override
    public List<Comment> getCommentsToVideo(Video video) {
        List<Comment> comments = video.getComments();
        Collections.reverse(comments);
        return comments;
    }
}
