package com.invicto.streaming_platform.persistence.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String text;

    @ManyToOne
    private User creator;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "date_of_public")
    private LocalDate dateOfPublic;

    public Comment(String text, User creator, LocalDate dateOfPublic) {
        this.text = text;
        this.creator = creator;
        this.dateOfPublic = dateOfPublic;
    }

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public LocalDate getDateOfPublic() {
        return dateOfPublic;
    }

    public void setDateOfPublic(LocalDate dateOfPublic) {
        this.dateOfPublic = dateOfPublic;
    }

}
