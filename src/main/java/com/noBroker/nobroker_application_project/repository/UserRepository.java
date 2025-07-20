package com.noBroker.nobroker_application_project.repository;

import com.chandu.NoBroker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
