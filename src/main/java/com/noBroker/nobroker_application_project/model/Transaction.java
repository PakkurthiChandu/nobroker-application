package com.noBroker.nobroker_application_project.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "transaction", indexes = {
        @Index(name = "idx_transaction_user", columnList = "user_id")
})
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
