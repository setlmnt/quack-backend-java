package com.backend.quack.service;

import com.backend.quack.domain.entity.Collection;
import com.backend.quack.domain.entity.Link;
import com.backend.quack.domain.repository.CollectionRepository;
import com.backend.quack.domain.repository.LinkRepository;
import com.backend.quack.dto.link.LinkPostDTO;
import com.backend.quack.dto.link.LinkPutDTO;
import com.backend.quack.dto.link.LinkResponseDTO;
import com.backend.quack.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;
    private final CollectionRepository collectionRepository;

    public List<LinkResponseDTO> listAllLinksInCollection(long collectionId) {
        List<Link> links = this.linkRepository.findAllByCollectionIdAndDeletedIsFalse(collectionId);
        return links
                .stream()
                .map(LinkResponseDTO::fromEntity)
                .toList();
    }

    public LinkResponseDTO findLinkById(long id) {
        Link link = this.linkRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Link not found"));
        return LinkResponseDTO.fromEntity(link);
    }

    public LinkResponseDTO saveLink(LinkPostDTO linkPostDTO) {
        Collection collection = collectionRepository
                .findByIdAndDeletedIsFalse(linkPostDTO.collection_id())
                .orElseThrow(() -> new EntityNotFoundException("Collection not Found"));

        Link link = linkPostDTO.toEntity();
        link.setCollection(collection);

        return LinkResponseDTO.fromEntity(this.linkRepository.save(link));
    }

    public LinkResponseDTO updateLinkById(long id, LinkPutDTO linkPutDTO) {
        Link link = this.linkRepository.getReferenceById(id);
        link.update(linkPutDTO.toEntity());
        return LinkResponseDTO.fromEntity(link);
    }

    public void deleteLinkById(long id) {
        Link link = this.linkRepository.getReferenceById(id);
        link.delete();
    }
}
