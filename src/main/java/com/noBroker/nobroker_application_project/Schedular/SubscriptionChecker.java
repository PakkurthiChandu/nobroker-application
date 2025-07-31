package com.noBroker.nobroker_application_project.Schedular;

import com.noBroker.nobroker_application_project.model.Transaction;
import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.repository.TransactionRepository;
import com.noBroker.nobroker_application_project.repository.UserRepository;
import com.noBroker.nobroker_application_project.service.EmailService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SubscriptionChecker {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final EmailService emailService;

    public SubscriptionChecker(UserRepository userRepository, TransactionRepository transactionRepository,
                               EmailService emailService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 00 9 * * ?", zone = "Asia/Kolkata")
    public void checkExpiredSubscriptions() {
        List<User> users = userRepository.findByIsSubscribedTrue();

        for (User user : users) {
            Transaction latestTransaction = transactionRepository.findTopByUserOrderByPaymentTimeDesc(user).orElse(null);

            if (latestTransaction != null) {
                LocalDateTime expiryDate = latestTransaction.getPaymentTime().plusMonths(1);

                if (expiryDate.isBefore(LocalDateTime.now())) {
//                    user.setIsSubscribed(false);

//                    userRepository.save(user);

                    emailService.sendSubscriptionExpiredEmail(user.getEmail(), user.getName());
                }
            }
        }
    }
}