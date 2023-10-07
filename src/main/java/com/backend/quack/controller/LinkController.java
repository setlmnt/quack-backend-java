package com.backend.quack.controller;

import com.backend.quack.dto.link.LinkPostDTO;
import com.backend.quack.dto.link.LinkPutDTO;
import com.backend.quack.dto.link.LinkResponseDTO;
import com.backend.quack.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections/{id}/links")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;

    @GetMapping()
    public List<LinkResponseDTO> listAllLinkInCollection(@PathVariable Long id) {
        return linkService.listAllLinksInCollection(id);
    }

    @GetMapping("/{linkId}")
    public LinkResponseDTO findLinkById(@PathVariable Long linkId, @PathVariable String id) {
        return linkService.findLinkById(linkId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LinkResponseDTO saveLink(
            @PathVariable String id,
            @RequestBody LinkPostDTO linkPostDTO
    ) {
        return linkService.saveLink(new LinkPostDTO(
                linkPostDTO.name(),
                linkPostDTO.url(),
                Long.parseLong(id)
        ));
    }

    @PutMapping("/{linkId}")
    public LinkResponseDTO updateLink(
            @PathVariable String id,
            @PathVariable Long linkId,
            @RequestBody LinkPutDTO linkPutDTO
    ) {
        return linkService.updateLinkById(linkId, linkPutDTO);
    }

    @DeleteMapping("/{linkId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLink(@PathVariable Long linkId, @PathVariable String id) {
        linkService.deleteLinkById(linkId);
    }
}
