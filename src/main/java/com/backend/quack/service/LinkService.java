package com.backend.quack.service;

import com.backend.quack.domain.entity.Collection;
import com.backend.quack.domain.entity.CollectionVisibility;
import com.backend.quack.domain.entity.Link;
import com.backend.quack.domain.repository.CollectionRepository;
import com.backend.quack.domain.repository.LinkRepository;
import com.backend.quack.dto.link.LinkPostDTO;
import com.backend.quack.dto.link.LinkPutDTO;
import com.backend.quack.dto.link.LinkResponseDTO;
import com.backend.quack.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;
    private final CollectionRepository collectionRepository;

    public Page<LinkResponseDTO> listAllLinksInCollection(String username, String slug, Pageable pageable) {
        Collection collection = collectionRepository.findBySlug(slug);

        if (collection == null) {
            throw new EntityNotFoundException("Collection not found");
        }

        if (collection.getVisibility().equals(CollectionVisibility.PRIVATE)) {
            if (username == null) throw new EntityNotFoundException("Collection not found");

            if (!collection.getUser().getUsername().equals(username)) {
                throw new EntityNotFoundException("Collection not found");
            }
        }

        Page<Link> links = this.linkRepository.findAllByCollectionSlugAndDeletedIsFalse(slug, pageable);
        return links.map(LinkResponseDTO::fromEntity);
    }

    public LinkResponseDTO findLinkById(String username, String slug, long id) {
        Collection collection = collectionRepository.findBySlug(slug);

        if (collection == null) {
            throw new EntityNotFoundException("Collection not found");
        }

        if (collection.getVisibility().equals(CollectionVisibility.PRIVATE)) {
            if (username == null) throw new EntityNotFoundException("Collection not found");

            if (!collection.getUser().getUsername().equals(username)) {
                throw new EntityNotFoundException("Collection not found");
            }
        }

        Link link = this.linkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Link not found"));
        return LinkResponseDTO.fromEntity(link);
    }

    public LinkResponseDTO saveLink(String username, String slug, LinkPostDTO linkPostDTO) {
        Collection collection = collectionRepository.findBySlug(slug);

        if (collection == null) {
            throw new EntityNotFoundException("Collection not found");
        }

        if (!collection.getUser().getUsername().equals(username)) {
            throw new EntityNotFoundException("Collection not found");
        }

        Link link = linkPostDTO.toEntity();
        link.setCollection(collection);

        return LinkResponseDTO.fromEntity(this.linkRepository.save(link));
    }

    public LinkResponseDTO updateLinkById(String username, long id, LinkPutDTO linkPutDTO) {
        Link link = this.linkRepository.getReferenceById(id);

        if (!link.getCollection().getUser().getUsername().equals(username)) {
            throw new EntityNotFoundException("Link not found");
        }

        link.update(linkPutDTO.toEntity());
        return LinkResponseDTO.fromEntity(link);
    }

    public void deleteLinkById(long id, String username) {
        Link link = this.linkRepository.getReferenceById(id);

        if (!link.getCollection().getUser().getUsername().equals(username)) {
            throw new EntityNotFoundException("Link not found");
        }

        link.delete();
    }
}
