package com.backend.quack.domain.repository;

import com.backend.quack.domain.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
    @Query("SELECT l FROM Link l WHERE l.collection.id = ?1 AND l.deleted = false")
    List<Link> findAllByCollectionIdAndDeletedIsFalse(long collectionId);

}
