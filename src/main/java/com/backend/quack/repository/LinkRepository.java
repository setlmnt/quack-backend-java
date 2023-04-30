package com.backend.quack.repository;

import com.backend.quack.model.LinkModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<LinkModel, Long> { }
