package com.noBroker.nobroker_application_project.repository;

import com.noBroker.nobroker_application_project.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Image,Long> {
}
