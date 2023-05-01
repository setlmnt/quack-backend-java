package com.backend.quack.repository;

import com.backend.quack.model.LinkModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LinkRepository extends JpaRepository<LinkModel, Long> {

    @Query("SELECT cm.links FROM CollectionModel cm WHERE cm.id = ?1")
    List<LinkModel> findAllByCollectionId(long collectionId);

}
