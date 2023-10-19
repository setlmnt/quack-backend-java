package com.backend.quack.dto.link;

import com.backend.quack.domain.entity.Link;

public record LinkResponseDTO(
        Long id,
        String name,
        String url
) {
    public static LinkResponseDTO fromEntity(Link link) {
        return new LinkResponseDTO(
                link.getId(),
                link.getName(),
                link.getUrl()
        );
    }

    public Link toEntity() {
        return new Link(
                id,
                name,
                url,
                null,
                null,
                null,
                null,
                null
        );
    }
}
