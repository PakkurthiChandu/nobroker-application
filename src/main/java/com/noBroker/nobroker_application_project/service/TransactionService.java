package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.Transaction;
import com.noBroker.nobroker_application_project.model.User;

import java.util.Set;

public interface TransactionService {

    Set<Transaction> getTransactionsByUserId(Long userId);

    void save(Transaction transaction);

    Transaction findTopByUserOrderByPaymentTimeDesc(User user);
}