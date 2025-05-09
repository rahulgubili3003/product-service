package com.online.store.controller;

import com.online.store.dto.InventoryRequest;
import com.online.store.dto.InventoryResponse;
import com.online.store.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/addInventory")
    public ResponseEntity<String> addInventory(@Validated @RequestBody final InventoryRequest inventoryRequest) {
        final InventoryResponse response = inventoryService.addInventory(inventoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toJson());
    }

    @GetMapping("/getInventory")
    public ResponseEntity<String> getInventory(@RequestParam final Long productId) {
        final InventoryResponse response = inventoryService.getInventory(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toJson());
    }
}