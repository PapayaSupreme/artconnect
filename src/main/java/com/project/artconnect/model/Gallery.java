package com.project.artconnect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "galleries")
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 180)
    private String name;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "owner_name", length = 150)
    private String ownerName;

    @Column(name = "opening_hours", length = 120)
    private String openingHours;

    @Column(name = "contact_phone", length = 50)
    private String contactPhone;

    @Column(name = "rating", nullable = false)
    private double rating;

    @Column(name = "website", length = 255)
    private String website;

    @OneToMany(mappedBy = "gallery")
    private List<Exhibition> exhibitions = new ArrayList<>();

    public Gallery() {
    }

    public Gallery(String name, String address, double rating) {
        this.name = name;
        this.address = address;
        this.rating = rating;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Exhibition> getExhibitions() {
        return exhibitions;
    }

    public void setExhibitions(List<Exhibition> exhibitions) {
        this.exhibitions = exhibitions;
    }

    public void addExhibition(Exhibition exhibition) {
        this.exhibitions.add(exhibition);
        if (exhibition.getGallery() != this) {
            exhibition.setGallery(this);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
