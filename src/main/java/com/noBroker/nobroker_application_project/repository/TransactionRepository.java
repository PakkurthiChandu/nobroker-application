package com.noBroker.nobroker_application_project.repository;

import com.noBroker.nobroker_application_project.model.Transaction;
import com.noBroker.nobroker_application_project.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findTopByUserOrderByPaymentTimeDesc(User user);

    Set<Transaction> findByUserUserId(Long userId);
}