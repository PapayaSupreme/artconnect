package com.project.artconnect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Artwork entity representing a piece created by an artist.
 */
@Entity
@Table(name = "artworks")
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 180)
    private String title;

    @Column(name = "creation_year")
    private Integer creationYear;

    @Column(name = "type", length = 100)
    private String type; // painting, sculpture, etc.

    @Column(name = "medium", length = 120)
    private String medium; // oil, watercolor, etc.

    @Column(name = "dimensions", length = 120)
    private String dimensions;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status; // FOR_SALE, SOLD, EXHIBITED

    @ManyToOne(optional = false)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @ManyToMany
    @JoinTable(
            name = "artwork_tags_map",
            joinColumns = @JoinColumn(name = "artwork_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<ArtworkTag> tags = new ArrayList<>();

    @ManyToMany(mappedBy = "artworks")
    private List<Exhibition> exhibitions = new ArrayList<>();

    @OneToMany(mappedBy = "artwork")
    private List<Review> reviews = new ArrayList<>();

    public enum Status {
        FOR_SALE, SOLD, EXHIBITED
    }

    public Artwork() {
    }

    public Artwork(String title, Integer creationYear, String type, double price, Artist artist) {
        this.title = title;
        this.creationYear = creationYear;
        this.type = type;
        this.price = price;
        this.artist = artist;
        this.status = Status.FOR_SALE;
    }

    // Getters and Setters
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

    public Integer getCreationYear() {
        return creationYear;
    }

    public void setCreationYear(Integer creationYear) {
        this.creationYear = creationYear;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<ArtworkTag> getTags() {
        return tags;
    }

    public void setTags(List<ArtworkTag> tags) {
        this.tags = tags;
    }

    public List<Exhibition> getExhibitions() {
        return exhibitions;
    }

    public void setExhibitions(List<Exhibition> exhibitions) {
        this.exhibitions = exhibitions;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return title;
    }
}
