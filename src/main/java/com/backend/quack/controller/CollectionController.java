package com.backend.quack.controller;

import com.backend.quack.dto.collection.CollectionDetailedResponseDTO;
import com.backend.quack.dto.collection.CollectionPostDTO;
import com.backend.quack.dto.collection.CollectionPutDTO;
import com.backend.quack.dto.collection.CollectionResponseDTO;
import com.backend.quack.service.CollectionService;
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
public class CollectionController {
    private final CollectionService collectionService;

    @GetMapping()
    public Page<CollectionResponseDTO> listAllCollections(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable
    ) {
        String username = userDetails == null ? null : userDetails.getUsername();
        return collectionService.findAllCollections(username, pageable);
    }

    @GetMapping("/users/{username}")
    public Page<CollectionResponseDTO> listAllCollections(
            @PathVariable String username,
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable
    ) {
        String userDetailsName = userDetails == null ? null : userDetails.getUsername();
        return collectionService.findAllCollectionsByUsername(username, userDetailsName, pageable);
    }

    @GetMapping("/{slug}")
    public CollectionResponseDTO findCollectionBySlug(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String slug
    ) {
        System.out.println("userDetails: " + userDetails);
        String username = userDetails == null ? null : userDetails.getUsername();
        return collectionService.findCollectionBySlug(slug, username);
    }


    @GetMapping("/{id}/detailed")
    @SecurityRequirement(name = "bearer-key")
    public CollectionDetailedResponseDTO findCollectionById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        return collectionService.findCollectionById(id, userDetails.getUsername());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearer-key")
    public CollectionResponseDTO saveCollection(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CollectionPostDTO collectionPostDTO
    ) {
        return collectionService.saveCollection(collectionPostDTO, userDetails.getUsername());
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public CollectionResponseDTO updateCollection(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CollectionPutDTO collectionPutDTO
    ) {
        return collectionService.updateCollection(id, collectionPutDTO, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCollection(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        collectionService.deleteCollection(id, userDetails.getUsername());
    }
}
