package com.invicto.streaming_platform.web.controller;

import com.invicto.streaming_platform.persistence.model.Video;
import com.invicto.streaming_platform.services.FileService;
import com.invicto.streaming_platform.services.VideoService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.Optional;

@Controller
@RequestMapping(value = "/")
public class VideoController {

    private final VideoService videoService;
    private final FileService fileService;

    public VideoController(VideoService videoService, FileService fileService) {
        this.videoService = videoService;
        this.fileService = fileService;
    }

    @GetMapping("/video")
    public String str0eam(Model model, @RequestParam String id) {
        Optional<Video> optionalVideo = videoService.findById(Long.parseLong(id));
        if (optionalVideo.isEmpty()) {
            return "error";
        }
        Video video = optionalVideo.get();
        model.addAttribute("video", video);
        return "video";
    }

    @PostMapping(value = "/video")
    public String incrementView(@RequestParam String id) {
        Optional<Video> optionalVideo = videoService.findById(Long.parseLong(id));
        if (optionalVideo.isEmpty()) {
            throw new IllegalArgumentException("There is no videos of that user");
        }
        Video video = optionalVideo.get();
        video.setViewsCount(video.getViewsCount()+1);
        videoService.updateVideo(video);
        return "redirect:/video?id="+id;
    }

    @GetMapping(value = "/video/stream")
    @ResponseBody
    public ResponseEntity<FileSystemResource> doStream(@RequestParam String id) {
        Path videoFile = fileService.findByVideoId(Long.parseLong(id));
        return ResponseEntity.ok().body(new FileSystemResource(videoFile));
    }
}
