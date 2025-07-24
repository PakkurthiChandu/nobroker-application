package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.Transaction;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public Transaction getLatestTransaction(User user) {
        return transactionRepository
                .findTopByUserOrderByPaymentTimeDesc(user)
                .orElse(null);
    }
}
