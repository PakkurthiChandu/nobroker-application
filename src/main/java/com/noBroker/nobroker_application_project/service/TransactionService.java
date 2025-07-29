package com.noBroker.nobroker_application_project.service;

import com.noBroker.nobroker_application_project.model.Transaction;
import com.noBroker.nobroker_application_project.model.User;

import java.util.List;

public interface TransactionService {

    Transaction getLatestTransaction(User user);

    List<Transaction> getTransactionsByUserId(Long userId);

    void save(Transaction transaction);

    Transaction findTopByUserOrderByPaymentTimeDesc(User user);
}