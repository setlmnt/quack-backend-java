package com.backend.quack.domain.repository;

import com.backend.quack.domain.entity.Link;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LinkRepository extends JpaRepository<Link, Long> {
    @Query("SELECT l FROM Link l WHERE l.collection.slug = :slug AND l.deleted = false")
    Page<Link> findAllByCollectionSlugAndDeletedIsFalse(String slug, Pageable pageable);
}
