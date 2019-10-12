package com.webbdealer.dms.infrastructure.dealer.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String year;

    private String make;

    private String model;

    private String trimlevel;

    @Column(insertable = true, updatable = false)
    private LocalDateTime createdAt;

    @Column(insertable = true, updatable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }



}
