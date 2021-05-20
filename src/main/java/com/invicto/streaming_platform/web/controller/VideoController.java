package com.invicto.streaming_platform.web.controller;

import com.invicto.streaming_platform.persistence.model.Comment;
import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.persistence.model.Video;
import com.invicto.streaming_platform.services.CommentService;
import com.invicto.streaming_platform.services.FileService;
import com.invicto.streaming_platform.services.UserService;
import com.invicto.streaming_platform.services.VideoService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/")
public class VideoController {

    private static final Logger logger = Logger.getLogger(VideoController.class.getName());
    private final UserService userService;
    private final VideoService videoService;
    private final FileService fileService;
    private final CommentService commentService;

    public VideoController(UserService userService, VideoService videoService, FileService fileService, CommentService commentService) {
        this.userService = userService;
        this.videoService = videoService;
        this.fileService = fileService;
        this.commentService = commentService;
    }

    @GetMapping("/video")
    public String stream(Model model, @RequestParam String id) {
        Optional<Video> optionalVideo = videoService.findById(Long.parseLong(id));
        if (optionalVideo.isEmpty()) {
            return "error";
        }
        Video video = optionalVideo.get();
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        try {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            String userEmail = principal.getUsername();
            Optional<User> currUserOptional = userService.findByEmail(userEmail);
            if (currUserOptional.isPresent()) {
                User currUser = currUserOptional.get();
                model.addAttribute("currUser", currUser);
            }
        } catch (ClassCastException e) {
            logger.info("Anonymous user");
        }
        model.addAttribute("video", video);
        model.addAttribute("comments", commentService.getCommentsToVideo(video));
        return "video";
    }

    @PostMapping(value = "/video")
    public String incrementView(@RequestParam String id) {
        Video video = videoService.findById(Long.parseLong(id)).orElseThrow(
                () -> new IllegalArgumentException("There is no videos of that user"));
        video.setViewsCount(video.getViewsCount()+1);
        videoService.updateVideo(video);
        return "redirect:/video?id="+id;
    }

    @GetMapping(value = "/video/stream")
    @ResponseBody
    public ResponseEntity<FileSystemResource> doStream(@RequestParam String id) {
        Path videoFile = fileService.findPathByVideoId(Long.parseLong(id));
        return ResponseEntity.ok().body(new FileSystemResource(videoFile));
    }

    @GetMapping(value = "/video/add_comment")
    public String addComment(@RequestParam Long id, @RequestParam String text, @RequestParam String creatorLogin) {
        Video video = videoService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("There is no videos of that user"));
        User creator = userService.findByLogin(creatorLogin).orElseThrow(
                () -> new IllegalArgumentException("There is no user with that login"));
        Comment comment = new Comment(text, creator, LocalDate.now());
        commentService.addCommentToVideo(comment, video);
        return "redirect:/video?id="+id;
    }
}
