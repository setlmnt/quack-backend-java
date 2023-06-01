package com.backend.quack.repository;

import com.backend.quack.domain.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Collection findBySlug(String slug);
}
