package com.webbdealer.dms.infrastructure.admin.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@ToString(exclude = { "account", "roles" })
@Entity(name = "User")
@Table(name = "users")
public class DmsUser  {

    public DmsUser() {}

    public DmsUser(String email, String password, String firstname, String lastname, String phone, String position, boolean admin) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.position = position;
        this.admin = admin;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    //@JoinColumn(name = "account_id", nullable = false, insertable = false, updatable = false) // insertable and updatable for TESTING ONLY
    @JsonBackReference
    private Account account;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Collection<Role> roles;

    private String email;

    private String password;

    private String firstname;

    private String lastname;

    private String phone;

    private String position;

    private boolean admin;

    private boolean active = true;

    private String rememberToken;

//    @Transient
//    private List<GrantedAuthority> roles;

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

//    @Override
//    public List<GrantedAuthority> getAuthorities(){
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.toString())));
//        return authorities;
//    }
}
