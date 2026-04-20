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

@Entity
@Table(name = "community_members")
public class CommunityMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "city", length = 120)
    private String city;

    @ManyToMany
    @JoinTable(
            name = "member_favorite_disciplines",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "discipline_id")
    )
    private List<Discipline> favoriteDisciplines = new ArrayList<>();

    @Column(name = "membership_type", length = 20)
    private String membershipType; // free, premium

    @OneToMany(mappedBy = "member")
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "reviewer")
    private List<Review> reviews = new ArrayList<>();

    public CommunityMember() {
    }

    public CommunityMember(String name, String email) {
        this.name = name;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
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

    public List<Discipline> getFavoriteDisciplines() {
        return favoriteDisciplines;
    }

    public void setFavoriteDisciplines(List<Discipline> favoriteDisciplines) {
        this.favoriteDisciplines = favoriteDisciplines;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
        if (booking.getMember() != this) {
            booking.setMember(this);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
