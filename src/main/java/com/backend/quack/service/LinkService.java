package com.backend.quack.service;

import com.backend.quack.domain.Collection;
import com.backend.quack.domain.Link;
import com.backend.quack.repository.LinkRepository;
import com.backend.quack.request.LinkPostRequestBody;
import com.backend.quack.request.LinkPutRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;
    private final CollectionService collectionService;

    public List<Link> listAllLinksInCollection(long collectionId) {
        return this.linkRepository.findAllByCollectionId(collectionId);
    }

    public Link findLinkById(long id) {
        return this.linkRepository.findById(id).orElseThrow();
    }

    public Link saveLink(LinkPostRequestBody linkPostRequestBody) {
        Collection collection = collectionService.findCollectionById(linkPostRequestBody.getCollection_id());
        return this.linkRepository.save(
                Link.builder()
                        .name(linkPostRequestBody.getName())
                        .url(linkPostRequestBody.getUrl())
                        .collection(collection)
                        .build()
        );
    }

    public void deleteLinkById(long id) {
        this.linkRepository.deleteById(id);
    }

    public Link updateLinkById(long id, LinkPutRequestBody linkPutRequestBody) {
        Link link = this.findLinkById(id);
        if (linkPutRequestBody.getName() != null) {
            link.setName(linkPutRequestBody.getName());
        }

        if (linkPutRequestBody.getUrl() != null) {
            link.setUrl(linkPutRequestBody.getUrl());
        }

        return this.linkRepository.save(link);
    }
}
