package com.backend.quack.service;

import com.backend.quack.domain.entity.Collection;
import com.backend.quack.domain.repository.CollectionRepository;
import com.backend.quack.dto.collection.CollectionDetailedResponseDTO;
import com.backend.quack.dto.collection.CollectionPostDTO;
import com.backend.quack.dto.collection.CollectionPutDTO;
import com.backend.quack.dto.collection.CollectionResponseDTO;
import com.backend.quack.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;

    public List<CollectionResponseDTO> findAllCollections() {
        return collectionRepository
                .findAllByDeletedIsFalse()
                .stream()
                .map(CollectionResponseDTO::fromEntity)
                .toList();
    }

    public CollectionResponseDTO findCollectionBySlug(String slug) {
        Collection collection = collectionRepository.findBySlugAndDeletedIsFalse(slug)
                .orElseThrow(() -> new EntityNotFoundException("Collection not Found"));
        return CollectionResponseDTO.fromEntity(collection);
    }

    public CollectionDetailedResponseDTO findCollectionById(Long id) {
        Collection collection = collectionRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Collection not Found"));
        return CollectionDetailedResponseDTO.fromEntity(collection);
    }

    public CollectionResponseDTO saveCollection(CollectionPostDTO collectionPostDTO) {
        Collection collection = collectionPostDTO.toEntity();

        Collection collectionBySlug = collectionRepository.findBySlug(Collection.slugify(collectionPostDTO.name()));

        if (collectionBySlug != null) {
            Random random = new Random();
            collection.setSlug(Collection.slugify(collectionPostDTO.name() + "-" + random.nextInt(10000)));
        } else {
            collection.setSlug(Collection.slugify(collectionPostDTO.name()));
        }

        return CollectionResponseDTO.fromEntity(collectionRepository.save(collection));
    }

    public CollectionResponseDTO updateCollection(Long id, CollectionPutDTO collectionPutDTO) {
        Collection collection = collectionRepository.getReferenceById(id);

        collection.update(collectionPutDTO.toEntity());

        Collection collectionBySlug = collectionRepository.findBySlug(collection.getSlug());
        if (collectionBySlug != null && !collectionBySlug.getId().equals(id)) {
            Random random = new Random();
            collection.setSlug(collection.getSlug() + "-" + random.nextInt(10000));
        }

        return CollectionResponseDTO.fromEntity(collectionRepository.save(collection));
    }

    public void deleteCollection(Long id) {
        Collection collection = collectionRepository.getReferenceById(id);
        collection.delete();
    }
}
