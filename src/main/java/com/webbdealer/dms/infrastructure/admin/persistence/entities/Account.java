package com.webbdealer.dms.infrastructure.admin.persistence.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString(exclude = { "dmsUsers" })
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dbPassword;

    private String apiToken;

    private String email;

    private String dealership;

    private boolean active;

    @OneToMany(mappedBy = "account", fetch=FetchType.EAGER)
    @JsonManagedReference
    private List<DmsUser> dmsUsers;

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
