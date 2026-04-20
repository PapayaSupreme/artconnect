package com.project.artconnect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "disciplines")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 120)
    private String name;

    @ManyToMany(mappedBy = "disciplines")
    private List<Artist> artists = new ArrayList<>();

    @ManyToMany(mappedBy = "favoriteDisciplines")
    private List<CommunityMember> communityMembers = new ArrayList<>();

    public Discipline() {
    }

    public Discipline(String name) {
        this.name = name;
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

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<CommunityMember> getCommunityMembers() {
        return communityMembers;
    }

    public void setCommunityMembers(List<CommunityMember> communityMembers) {
        this.communityMembers = communityMembers;
    }

    @Override
    public String toString() {
        return name;
    }
}
