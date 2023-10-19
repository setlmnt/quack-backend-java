package com.backend.quack.dto.collection;

import com.backend.quack.domain.entity.Collection;
import com.backend.quack.domain.entity.CollectionVisibility;

import java.time.LocalDateTime;

public record CollectionDetailedResponseDTO(
        Long id,
        String name,
        String slug,
        String bio,
        CollectionVisibility visibility,
        Boolean deleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CollectionDetailedResponseDTO fromEntity(Collection collection) {
        return new CollectionDetailedResponseDTO(
                collection.getId(),
                collection.getName(),
                collection.getSlug(),
                collection.getBio(),
                collection.getVisibility(),
                collection.getDeleted(),
                collection.getCreatedAt(),
                collection.getUpdatedAt()
        );
    }

    public Collection toEntity() {
        return new Collection(
                id,
                name,
                slug,
                bio,
                null,
                deleted,
                visibility,
                null,
                createdAt,
                updatedAt,
                null
        );
    }
}
