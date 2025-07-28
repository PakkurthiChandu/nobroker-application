package com.noBroker.nobroker_application_project.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String mobilePhone;
    private String role;
    private Boolean isSubscribed = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_bookmarks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
    )
    private Set<Property> bookmarkedProperties =  new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Property> properties = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();
}
