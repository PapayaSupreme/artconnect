package com.project.artconnect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Artist entity representing a creator in the community.
 */
@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 150)
    private String name;

    @Column(name = "bio")
    private String bio;

    @Column(name = "birth_year")
    private Integer birthYear;

    @ManyToMany
    @JoinTable(
            name = "artist_disciplines",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "discipline_id")
    )
    private List<Discipline> disciplines = new ArrayList<>();

    @Column(name = "contact_email", unique = true, length = 255)
    private String contactEmail;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "city", length = 120)
    private String city;

    @Column(name = "website", length = 255)
    private String website;

    @Column(name = "social_media", length = 255)
    private String socialMedia;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "artist")
    private List<Artwork> artworks = new ArrayList<>();

    @OneToMany(mappedBy = "instructor")
    private List<Workshop> workshops = new ArrayList<>();

    public Artist() {
    }

    public Artist(String name, String bio, Integer birthYear, String contactEmail, String city) {
        this.name = name;
        this.bio = bio;
        this.birthYear = birthYear;
        this.contactEmail = contactEmail;
        this.city = city;
        this.isActive = true;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    public List<Workshop> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
    }

    public void addArtwork(Artwork artwork) {
        this.artworks.add(artwork);
        if (artwork.getArtist() != this) {
            artwork.setArtist(this);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
