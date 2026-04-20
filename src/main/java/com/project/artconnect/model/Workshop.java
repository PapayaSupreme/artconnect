package com.project.artconnect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workshops")
public class Workshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 180)
    private String title;

    @Column(name = "workshop_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Artist instructor;

    @Column(name = "location", length = 180)
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "level", length = 20)
    private String level; // beginner, intermediate, advanced

    @OneToMany(mappedBy = "workshop")
    private List<Booking> bookings = new ArrayList<>();

    public Workshop() {
    }

    public Workshop(String title, LocalDateTime date, Artist instructor, double price) {
        this.title = title;
        this.date = date;
        this.instructor = instructor;
        this.price = price;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Artist getInstructor() {
        return instructor;
    }

    public void setInstructor(Artist instructor) {
        this.instructor = instructor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return title;
    }
}
