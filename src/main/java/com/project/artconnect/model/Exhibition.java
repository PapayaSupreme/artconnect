package com.project.artconnect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exhibitions")
public class Exhibition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true, length = 180)
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;

    @Column(name = "curator_name", length = 150)
    private String curatorName;

    @Column(name = "theme", length = 120)
    private String theme;

    @ManyToMany
    @JoinTable(
            name = "exhibition_artworks",
            joinColumns = @JoinColumn(name = "exhibition_id"),
            inverseJoinColumns = @JoinColumn(name = "artwork_id")
    )
    private List<Artwork> artworks = new ArrayList<>();

    public Exhibition() {
    }

    public Exhibition(String title, LocalDate startDate, LocalDate endDate, Gallery gallery) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.gallery = gallery;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public String getCuratorName() {
        return curatorName;
    }

    public void setCuratorName(String curatorName) {
        this.curatorName = curatorName;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    @Override
    public String toString() {
        return title;
    }
}
