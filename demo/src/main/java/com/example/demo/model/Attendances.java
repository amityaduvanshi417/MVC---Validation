package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@Entity
public class Attendances {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attendance_id;
    @Column(name = "createdat", updatable = false)
    private LocalDate createdat;
    private LocalDate updatedAt;
    private String status;
    private String Type;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @PrePersist
    protected void onCreate() {
        LocalDate now = LocalDate.now();
        this.createdat = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }

}
