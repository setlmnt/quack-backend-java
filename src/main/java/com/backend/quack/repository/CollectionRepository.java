package com.backend.quack.repository;

import com.backend.quack.model.CollectionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<CollectionModel, Long> {
    CollectionModel findBySlug(String slug);
}
