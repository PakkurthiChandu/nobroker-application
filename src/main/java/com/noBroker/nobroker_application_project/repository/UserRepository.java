package com.noBroker.nobroker_application_project.repository;

import com.noBroker.nobroker_application_project.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.mobilePhone = :mobilePhone AND u.userId <> :userId")
    Optional<User> findByMobilePhoneUserIdNot(@Param("mobilePhone") String mobilePhone, @Param("userId") Long userId);

    List<User> findByIsSubscribedTrue();

    Optional<User> findByMobilePhone(String username);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.userId <> :userId")
    User findByEmailUserIdNot(@Param("email") String email, @Param("userId") Long  userId);
}
