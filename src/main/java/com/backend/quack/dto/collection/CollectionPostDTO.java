package com.backend.quack.dto.collection;

import com.backend.quack.domain.entity.Collection;
import com.backend.quack.domain.entity.CollectionVisibility;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CollectionPostDTO(
        @NotBlank(message = "Collection name is required")
        @Size(min = 3, max = 30, message = "Collection name must be between 3 and 20 characters")
        String name,

        @Size(max = 100, message = "Collection bio must be less than 100 characters")
        String bio,

        @Enumerated(EnumType.STRING)
        CollectionVisibility visibility
) {
    public static CollectionPostDTO fromEntity(Collection collection) {
        return new CollectionPostDTO(
                collection.getName(),
                collection.getBio(),
                collection.getVisibility()
        );
    }

    public Collection toEntity() {
        return new Collection(
                null,
                name,
                null,
                bio,
                false,
                visibility,
                null,
                null,
                null
        );
    }
}
