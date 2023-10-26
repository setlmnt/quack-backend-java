package com.backend.quack.controller;

import com.backend.quack.dto.link.LinkPostDTO;
import com.backend.quack.dto.link.LinkPutDTO;
import com.backend.quack.dto.link.LinkResponseDTO;
import com.backend.quack.service.LinkService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/collections")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;

    @GetMapping("{slug}/links")
    public Page<LinkResponseDTO> listAllLinkInCollection(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String slug,
            Pageable pageable
    ) {
        String username = userDetails == null ? null : userDetails.getUsername();
        return linkService.listAllLinksInCollection(username, slug, pageable);
    }

    @GetMapping("{slug}/links/{id}")
    public LinkResponseDTO findLinkById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String slug,
            @PathVariable Long id
    ) {
        String username = userDetails == null ? null : userDetails.getUsername();
        return linkService.findLinkById(username, slug, id);
    }

    @PostMapping("{slug}/links")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearer-key")
    public LinkResponseDTO saveLink(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String slug,
            @RequestBody LinkPostDTO linkPostDTO
    ) {
        return linkService.saveLink(userDetails.getUsername(), slug, linkPostDTO);
    }

    @PutMapping("links/{id}")
    @SecurityRequirement(name = "bearer-key")
    public LinkResponseDTO updateLink(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody LinkPutDTO linkPutDTO
    ) {
        return linkService.updateLinkById(userDetails.getUsername(), id, linkPutDTO);
    }

    @DeleteMapping("links/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearer-key")
    public void deleteLink(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        linkService.deleteLinkById(id, userDetails.getUsername());
    }
}
