package com.invicto.streaming_platform.web.controller;

import com.invicto.streaming_platform.persistence.model.Video;
import com.invicto.streaming_platform.services.FileService;
import com.invicto.streaming_platform.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Controller
public class HomeController {

    private final VideoService videoService;
    private final FileService fileService;

    @Autowired
    public HomeController(VideoService videoService, FileService fileService) {
        this.videoService = videoService;
        this.fileService = fileService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/more_videos")
    public String moreVideos(@RequestParam int row, @RequestParam int size, Model model) {
        List<Video> videos = videoService.getPageSortedByViewsCount(row, size);
        model.addAttribute("videos", videos);
        return "video_cards";
    }

    @GetMapping("/thumbnail/{id}")
    @ResponseBody
    public ResponseEntity<FileSystemResource> thumbnail(@PathVariable long id) {
        Path imagePath = fileService.findThumbnailPathById(id).orElseThrow(
                () -> new IllegalArgumentException("Thumbnail not found"));
        File imageFile = imagePath.toFile();
        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        String mimeType = fileTypeMap.getContentType(imageFile.getName());
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(mimeType))
                .body(new FileSystemResource(imageFile));
    }
}
