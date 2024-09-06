package com.example.demo.model;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.sql.Timestamp;
import java.time.Instant;

public class EntityListener {
    @PrePersist
    public void prePersist(Admin admin) {
        Timestamp now = Timestamp.from(Instant.now());
        admin.setCreatedAt(now);
        admin.setUpdatedAt(now);
    }

    @PreUpdate
    public void preUpdate(Admin admin) {
        admin.setUpdatedAt(Timestamp.from(Instant.now()));
    }

}
