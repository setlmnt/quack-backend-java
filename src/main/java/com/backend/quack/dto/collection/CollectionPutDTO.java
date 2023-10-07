package com.backend.quack.dto.collection;

import com.backend.quack.domain.entity.Collection;
import com.backend.quack.domain.entity.CollectionVisibility;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;

public record CollectionPutDTO(
        @Nullable
        @Size(min = 3, max = 30, message = "Collection name must be between 3 and 20 characters")
        String name,

        @Nullable
        @Size(max = 100, message = "Collection bio must be less than 100 characters")
        String bio,

        @Nullable
        @Enumerated(EnumType.STRING)
        CollectionVisibility visibility
) {
    public static CollectionPutDTO fromEntity(Collection collection) {
        return new CollectionPutDTO(
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
