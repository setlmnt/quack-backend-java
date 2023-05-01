package com.backend.quack.request;

import com.backend.quack.model.CollectionVisibility;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CollectionPostRequestBody {
    @NotBlank(message = "Collection name is required")
    @Size(min = 3, max = 30, message = "Collection name must be between 3 and 20 characters")
    private String name;

    @Nullable
    @Size(max = 100, message = "Collection bio must be less than 100 characters")
    private String bio;

    @Nullable
    @Enumerated(EnumType.STRING)
    private CollectionVisibility visibility = CollectionVisibility.PUBLIC;
}
