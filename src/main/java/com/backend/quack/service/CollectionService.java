package com.backend.quack.service;

import com.backend.quack.domain.entity.Collection;
import com.backend.quack.domain.entity.CollectionVisibility;
import com.backend.quack.domain.entity.User;
import com.backend.quack.domain.repository.CollectionRepository;
import com.backend.quack.domain.repository.UserRepository;
import com.backend.quack.dto.collection.CollectionDetailedResponseDTO;
import com.backend.quack.dto.collection.CollectionPostDTO;
import com.backend.quack.dto.collection.CollectionPutDTO;
import com.backend.quack.dto.collection.CollectionResponseDTO;
import com.backend.quack.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;

    public Page<CollectionResponseDTO> findAllCollections(String username, Pageable pageable) {
        if (username == null) {
            Page<Collection> collections = collectionRepository
                    .findAllByVisibilityAndDeletedIsFalse(CollectionVisibility.PUBLIC, pageable);
            return collections.map(CollectionResponseDTO::fromEntity);
        }

        Page<Collection> collections = collectionRepository
                .findAllByUsernameOrVisibilityAndDeletedIsFalse(username, CollectionVisibility.PUBLIC, pageable);
        return collections.map(CollectionResponseDTO::fromEntity);
    }

    public Page<CollectionResponseDTO> findAllCollectionsByUsername(String username, String userDetailsName, Pageable pageable) {
        if (userDetailsName != null) {
            if (userDetailsName.equals(username)) {
                Page<Collection> collections = collectionRepository
                        .findAllByUsernameAndDeletedIsFalse(username, pageable);
                return collections.map(CollectionResponseDTO::fromEntity);

            }
        }

        Page<Collection> collections = collectionRepository
                .findAllByVisibilityAndUsernameAndDeletedIsFalse(username, CollectionVisibility.PUBLIC, pageable);
        return collections.map(CollectionResponseDTO::fromEntity);
    }

    public CollectionResponseDTO findCollectionBySlug(String slug, String username) {
        Collection collection = collectionRepository.findBySlugAndDeletedIsFalse(slug)
                .orElseThrow(() -> new EntityNotFoundException("Collection not Found"));

        if (collection.getVisibility().equals(CollectionVisibility.PRIVATE) && !collection.getUser().getUsername().equals(username)) {
            throw new EntityNotFoundException("Collection not Found");
        }

        return CollectionResponseDTO.fromEntity(collection);
    }

    public CollectionDetailedResponseDTO findCollectionById(Long id, String username) {
        Collection collection = collectionRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Collection not Found"));

        if (!collection.getUser().getUsername().equals(username)) {
            throw new EntityNotFoundException("Collection not Found");
        }

        return CollectionDetailedResponseDTO.fromEntity(collection);
    }

    public CollectionResponseDTO saveCollection(CollectionPostDTO collectionPostDTO, String username) {
        User user = userRepository.findUserByUsername(username);

        Collection collection = collectionPostDTO.toEntity();

        Collection collectionBySlug = collectionRepository.findBySlug(Collection.slugify(collectionPostDTO.name()));

        if (collectionBySlug != null) {
            Random random = new Random();
            collection.setSlug(Collection.slugify(collectionPostDTO.name() + "-" + random.nextInt(10000)));
        } else {
            collection.setSlug(Collection.slugify(collectionPostDTO.name()));
        }

        collection.setUser(user);

        return CollectionResponseDTO.fromEntity(collectionRepository.save(collection));
    }

    public CollectionResponseDTO updateCollection(Long id, CollectionPutDTO collectionPutDTO, String username) {
        Collection collection = collectionRepository.getReferenceById(id);

        if (!collection.getUser().getUsername().equals(username)) {
            throw new EntityNotFoundException("Collection not Found");
        }

        collection.update(collectionPutDTO.toEntity());

        Collection collectionBySlug = collectionRepository.findBySlug(collection.getSlug());
        if (collectionBySlug != null && !collectionBySlug.getId().equals(id)) {
            Random random = new Random();
            collection.setSlug(collection.getSlug() + "-" + random.nextInt(10000));
        }

        return CollectionResponseDTO.fromEntity(collectionRepository.save(collection));
    }

    public void deleteCollection(Long id, String username) {
        Collection collection = collectionRepository.getReferenceById(id);

        if (!collection.getUser().getUsername().equals(username)) {
            throw new EntityNotFoundException("Collection not Found");
        }

        collection.delete();
    }
}
