package com.noBroker.nobroker_application_project.service.serviceimpl;

import com.noBroker.nobroker_application_project.model.Transaction;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.TransactionRepository;
import com.noBroker.nobroker_application_project.service.TransactionService;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Set<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserUserId(userId);
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public Transaction findTopByUserOrderByPaymentTimeDesc(User user) {
        return transactionRepository.findTopByUserOrderByPaymentTimeDesc(user).orElse(null);
    }
}