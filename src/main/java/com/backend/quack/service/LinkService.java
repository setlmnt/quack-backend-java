package com.backend.quack.service;

import com.backend.quack.model.CollectionModel;
import com.backend.quack.model.LinkModel;
import com.backend.quack.repository.LinkRepository;
import com.backend.quack.request.LinkPostRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;
    private final CollectionService collectionService;

    public List<LinkModel> listAllLinksInCollection(long collectionId) {
        return this.linkRepository.findAllByCollectionId(collectionId);
    }

    public LinkModel findLinkById(long id) {
        return this.linkRepository.findById(id).orElseThrow();
    }

    public LinkModel saveLink(LinkPostRequestBody linkPostRequestBody) {
        CollectionModel collection = this.collectionService.findCollectionById(linkPostRequestBody.getCollection_id());
        return this.linkRepository.save(
                LinkModel.builder()
                        .name(linkPostRequestBody.getName())
                        .url(linkPostRequestBody.getUrl())
                        .collection(collection)
                        .build()
        );
    }

    public void deleteLinkById(long id) {
        this.linkRepository.deleteById(id);
    }

    public LinkModel updateLinkById(long id, LinkPostRequestBody linkPostRequestBody) {
        LinkModel link = this.findLinkById(id);
        if (linkPostRequestBody.getName() != null) {
            link.setName(linkPostRequestBody.getName());
        }

        if (linkPostRequestBody.getUrl() != null) {
            link.setUrl(linkPostRequestBody.getUrl());
        }

        return this.linkRepository.save(link);
    }
}
