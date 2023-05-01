package com.backend.quack.controller;

import com.backend.quack.model.LinkModel;
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
    public ResponseEntity<List<LinkModel>> listAllLinkInCollection(@PathVariable Long id) {
        return ResponseEntity.ok(linkService.listAllLinksInCollection(id));
    }

    @GetMapping("/{linkId}")
    public ResponseEntity<LinkModel> findLinkById(@PathVariable Long linkId, @PathVariable String id) {
        return ResponseEntity.ok(linkService.findLinkById(linkId));
    }

    @PostMapping()
    public ResponseEntity<LinkModel> saveLink(@RequestBody LinkPostRequestBody linkPostRequestBody, @PathVariable String id) {
        linkPostRequestBody.setCollection_id(Long.parseLong(id));
        System.out.println(linkPostRequestBody);
        return new ResponseEntity<>(linkService.saveLink(linkPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/{linkId}")
    public ResponseEntity<LinkModel> updateLink(
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
