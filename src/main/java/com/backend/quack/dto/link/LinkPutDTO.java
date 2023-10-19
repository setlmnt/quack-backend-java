package com.backend.quack.dto.link;

import com.backend.quack.domain.entity.Link;

public record LinkPutDTO(
        String name,
        String url,
        Long collection_id
) {
    public static LinkPutDTO fromEntity(Link link) {
        return new LinkPutDTO(
                link.getName(),
                link.getUrl(),
                link.getCollection().getId()
        );
    }

    public Link toEntity() {
        return new Link(
                null,
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
