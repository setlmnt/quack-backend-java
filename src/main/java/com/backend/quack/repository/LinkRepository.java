package com.backend.quack.repository;

import com.backend.quack.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {

    @Query("SELECT cm.links FROM Collection cm WHERE cm.id = ?1")
    List<Link> findAllByCollectionId(long collectionId);

}
