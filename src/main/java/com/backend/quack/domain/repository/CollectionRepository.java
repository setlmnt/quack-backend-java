package com.backend.quack.domain.repository;

import com.backend.quack.domain.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Collection findBySlug(String slug);

    Optional<Collection> findBySlugAndDeletedIsFalse(String slug);

    List<Collection> findAllByDeletedIsFalse();

    Optional<Collection> findByIdAndDeletedIsFalse(Long id);
}
