package com.invicto.streaming_platform.web.controller;

import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.persistence.model.Video;
import com.invicto.streaming_platform.services.FileService;
import com.invicto.streaming_platform.services.UserService;
import com.invicto.streaming_platform.services.VideoService;
import com.invicto.streaming_platform.web.dto.VideoDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class FileController {

    private final FileService fileService;
    private final UserService userService;
    private final VideoService videoService;

    public FileController(FileService fileService, UserService userService, VideoService videoService) {
        this.fileService = fileService;
        this.userService = userService;
        this.videoService = videoService;
    }

    @GetMapping("/upload")
    public String index(Model model) {
        VideoDto video = new VideoDto();
        model.addAttribute("fileData", video);
        return "upload";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@Valid @ModelAttribute("fileData") VideoDto videoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "upload";
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = ((UserDetails) principal).getUsername();
        Optional<User> creatorOptional = userService.findByEmail(userEmail);
        if (creatorOptional.isPresent()) {
            User creator = creatorOptional.get();
            String title = videoDto.getTitle();
            String description = videoDto.getDescription();

            Video video = new Video(title, description, creator, LocalDate.now());
            video = videoService.createVideo(video);
            fileService.uploadFile(videoDto.getSource(), video);
            fileService.uploadFile(videoDto.getThumbnail(), video);
        }
        return "redirect:/";
    }
}
