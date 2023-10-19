package com.backend.quack.dto.link;

import com.backend.quack.domain.entity.Link;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record LinkPostDTO(
        @NotBlank
        String name,

        @NotBlank
        @URL
        String url

) {
    public static LinkPostDTO fromEntity(Link link) {
        return new LinkPostDTO(
                link.getName(),
                link.getUrl()
        );
    }

    public Link toEntity() {
        return new Link(
                null,
                name,
                url,
                false,
                null,
                null,
                null,
                null
        );
    }
}
