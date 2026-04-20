package com.project.artconnect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reviews")
@IdClass(Review.ReviewId.class)
public class Review {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "reviewer_member_id", nullable = false)
    private CommunityMember reviewer;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "artwork_id", nullable = false)
    private Artwork artwork;

    @Column(name = "rating", nullable = false)
    private int rating; // 1-5

    @Column(name = "comment")
    private String comment;

    @Column(name = "review_date", nullable = false)
    private LocalDate reviewDate;

    public Review() {
    }

    public Review(CommunityMember reviewer, Artwork artwork, int rating, String comment) {
        this.reviewer = reviewer;
        this.artwork = artwork;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = LocalDate.now();
    }

    public CommunityMember getReviewer() {
        return reviewer;
    }

    public void setReviewer(CommunityMember reviewer) {
        this.reviewer = reviewer;
    }

    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public static class ReviewId implements Serializable {
        private Long reviewer;
        private Long artwork;

        public ReviewId() {
        }

        public ReviewId(Long reviewer, Long artwork) {
            this.reviewer = reviewer;
            this.artwork = artwork;
        }

        public Long getReviewer() {
            return reviewer;
        }

        public void setReviewer(Long reviewer) {
            this.reviewer = reviewer;
        }

        public Long getArtwork() {
            return artwork;
        }

        public void setArtwork(Long artwork) {
            this.artwork = artwork;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ReviewId)) {
                return false;
            }
            ReviewId that = (ReviewId) o;
            return Objects.equals(reviewer, that.reviewer) && Objects.equals(artwork, that.artwork);
        }

        @Override
        public int hashCode() {
            return Objects.hash(reviewer, artwork);
        }
    }
}
