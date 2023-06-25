package com.backend.quack.controller;

import com.backend.quack.domain.Link;
import com.backend.quack.request.LinkPostRequestBody;
import com.backend.quack.request.LinkPutRequestBody;
import com.backend.quack.service.LinkService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections/{id}/links")
@AllArgsConstructor
public class LinkController {
    private final LinkService linkService;

    @GetMapping()
    public ResponseEntity<List<Link>> listAllLinkInCollection(@PathVariable Long id) {
        return ResponseEntity.ok(linkService.listAllLinksInCollection(id));
    }

    @GetMapping("/{linkId}")
    public ResponseEntity<Link> findLinkById(@PathVariable Long linkId, @PathVariable String id) {
        return ResponseEntity.ok(linkService.findLinkById(linkId));
    }

    @PostMapping()
    public ResponseEntity<Link> saveLink(@RequestBody LinkPostRequestBody linkPostRequestBody, @PathVariable String id) {
        linkPostRequestBody.setCollection_id(Long.parseLong(id));
        return new ResponseEntity<>(linkService.saveLink(linkPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/{linkId}")
    public ResponseEntity<Link> updateLink(
            @PathVariable Long linkId, @RequestBody LinkPutRequestBody linkPutRequestBody,
            @PathVariable String id) {
        return ResponseEntity.ok(linkService.updateLinkById(linkId, linkPutRequestBody));
    }

    @DeleteMapping("/{linkId}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long linkId, @PathVariable String id) {
        linkService.deleteLinkById(linkId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
