package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Admin")
@EntityListeners(EntityListener.class)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 225)
    private String name;

    @Column(nullable = false, length = 225)
    private String email;

    @Column(nullable = false, length = 225)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean status = true;

    // Constructors
    public Admin() {
        this.status = true; // Set status to true by default
    }

    public Admin(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = true; // Set status to true by default
    }

}
