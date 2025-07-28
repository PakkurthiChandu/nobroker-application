package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.Transaction;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.TransactionRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction getLatestTransaction(User user) {
        return transactionRepository
                .findTopByUserOrderByPaymentTimeDesc(user)
                .orElse(null);
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserUserId(userId);
    }
}