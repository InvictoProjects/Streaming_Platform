package com.invicto.streaming_platform.persistence.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Long viewsCount;

    private String creatorId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "date_of_public")
    private LocalDate dateOfPublic;

    public Video() {}

    public Video(String title, String description, String creatorId, LocalDate dateOfPublic) {
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.dateOfPublic = dateOfPublic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Long viewsCount) {
        this.viewsCount = viewsCount;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDate getDateOfPublic() {
        return dateOfPublic;
    }

    public void setDateOfPublic(LocalDate dateOfPublic) {
        this.dateOfPublic = dateOfPublic;
    }
}
