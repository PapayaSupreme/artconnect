package com.project.artconnect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bookings")
@IdClass(Booking.BookingId.class)
public class Booking {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "workshop_id", nullable = false)
    private Workshop workshop;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private CommunityMember member;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus; // PENDING, PAID, CANCELLED

    public Booking() {
    }

    public Booking(Workshop workshop, CommunityMember member) {
        this.workshop = workshop;
        this.member = member;
        this.bookingDate = LocalDateTime.now();
        this.paymentStatus = "PENDING";
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public CommunityMember getMember() {
        return member;
    }

    public void setMember(CommunityMember member) {
        this.member = member;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public static class BookingId implements Serializable {
        private Long workshop;
        private Long member;

        public BookingId() {
        }

        public BookingId(Long workshop, Long member) {
            this.workshop = workshop;
            this.member = member;
        }

        public Long getWorkshop() {
            return workshop;
        }

        public void setWorkshop(Long workshop) {
            this.workshop = workshop;
        }

        public Long getMember() {
            return member;
        }

        public void setMember(Long member) {
            this.member = member;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof BookingId)) {
                return false;
            }
            BookingId that = (BookingId) o;
            return Objects.equals(workshop, that.workshop) && Objects.equals(member, that.member);
        }

        @Override
        public int hashCode() {
            return Objects.hash(workshop, member);
        }
    }
}
