package com.backend.quack.controller;

import com.backend.quack.domain.Collection;
import com.backend.quack.request.CollectionPostRequestBody;
import com.backend.quack.request.CollectionPutRequestBody;
import com.backend.quack.service.CollectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
@AllArgsConstructor
public class CollectionController {
    private final CollectionService collectionService;

    @GetMapping()
    public ResponseEntity<List<Collection>> listAllCollections() {
        return ResponseEntity.ok(collectionService.findAllCollections());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Collection> findCollectionBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(collectionService.findCollectionBySlug(slug));
    }

    @PostMapping()
    public ResponseEntity<Collection> saveCollection(@RequestBody CollectionPostRequestBody collectionPostRequestBody) {
        return new ResponseEntity<>(collectionService.saveCollection(collectionPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Collection> updateCollection(
            @PathVariable Long id, @RequestBody CollectionPutRequestBody collectionPutRequestBody
    ) {
        return ResponseEntity.ok(collectionService.updateCollection(id, collectionPutRequestBody));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
