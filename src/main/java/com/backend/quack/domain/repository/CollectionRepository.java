package com.backend.quack.domain.repository;

import com.backend.quack.domain.entity.Collection;
import com.backend.quack.domain.entity.CollectionVisibility;
import com.backend.quack.service.CollectionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Collection findBySlug(String slug);

    Optional<Collection> findBySlugAndDeletedIsFalse(String slug);

    List<Collection> findAllByDeletedIsFalse();

    Optional<Collection> findByIdAndDeletedIsFalse(Long id);

    Page<Collection> findAllByVisibilityAndDeletedIsFalse(CollectionVisibility collectionVisibility, Pageable pageable);

    @Query("SELECT c FROM Collection c WHERE c.deleted = false AND (c.user.username = :username OR c.visibility = :collectionVisibility)")
    Page<Collection> findAllByUsernameOrVisibilityAndDeletedIsFalse(String username, CollectionVisibility collectionVisibility, Pageable pageable);

    @Query("SELECT c FROM Collection c WHERE c.deleted = false AND c.user.username = :username AND c.visibility = :collectionVisibility")
    Page<Collection> findAllByVisibilityAndUsernameAndDeletedIsFalse(String username, CollectionVisibility collectionVisibility, Pageable pageable);

    @Query("SELECT c FROM Collection c WHERE c.deleted = false AND c.user.username = :username")
    Page<Collection> findAllByUsernameAndDeletedIsFalse(String username, Pageable pageable);
}
