package com.backend.quack.repository;

import com.backend.quack.model.LinkModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<LinkModel, Long> {

    List<LinkModel> findAllByCollectionId(long collectionId);

}
