package com.backend.quack.controller;

import com.backend.quack.dto.collection.CollectionDetailedResponseDTO;
import com.backend.quack.dto.collection.CollectionPostDTO;
import com.backend.quack.dto.collection.CollectionPutDTO;
import com.backend.quack.dto.collection.CollectionResponseDTO;
import com.backend.quack.service.CollectionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/collections")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class CollectionController {
    private final CollectionService collectionService;

    @GetMapping()
    public List<CollectionResponseDTO> listAllCollections() {
        return collectionService.findAllCollections();
    }

    @GetMapping("/{slug}")
    public CollectionResponseDTO findCollectionBySlug(@PathVariable String slug) {
        return collectionService.findCollectionBySlug(slug);
    }

    @GetMapping("/{id}/detailed")
    public CollectionDetailedResponseDTO findCollectionById(@PathVariable Long id) {
        return collectionService.findCollectionById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CollectionResponseDTO saveCollection(@RequestBody CollectionPostDTO collectionPostDTO) {
        return collectionService.saveCollection(collectionPostDTO);
    }

    @PutMapping("/{id}")
    public CollectionResponseDTO updateCollection(
            @PathVariable Long id,
            @RequestBody CollectionPutDTO collectionPutDTO
    ) {
        return collectionService.updateCollection(id, collectionPutDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
    }
}
