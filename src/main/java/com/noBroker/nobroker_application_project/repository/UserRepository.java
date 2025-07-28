package com.noBroker.nobroker_application_project.repository;

import com.noBroker.nobroker_application_project.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByMobilePhone(String mobilePhone);
    List<User> findByIsSubscribedTrue();
}
