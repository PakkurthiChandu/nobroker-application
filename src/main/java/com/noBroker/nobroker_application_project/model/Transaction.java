package com.noBroker.nobroker_application_project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @CreationTimestamp
    private LocalDateTime paymentTime;

    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
