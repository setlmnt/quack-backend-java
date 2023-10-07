package com.backend.quack.dto.collection;

import com.backend.quack.domain.entity.Collection;

public record CollectionResponseDTO(
        Long id,
        String name,
        String slug,
        String bio
) {
    public static CollectionResponseDTO fromEntity(Collection collection) {
        return new CollectionResponseDTO(
                collection.getId(),
                collection.getName(),
                collection.getSlug(),
                collection.getBio()
        );
    }

    public Collection toEntity() {
        return new Collection(
                id,
                name,
                slug,
                bio,
                false,
                null,
                null,
                null,
                null
        );
    }
}
