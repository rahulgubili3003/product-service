package com.online.store.controller;

import com.online.store.dto.InventoryRequest;
import com.online.store.dto.InventoryResponse;
import com.online.store.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/add")
    public ResponseEntity<String> addInventory(@Validated @RequestBody final InventoryRequest inventoryRequest) {
        inventoryService.addInventory(inventoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"Inventory added successfully\"}");
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable final Long productId) {
        final InventoryResponse inventoryResponse = inventoryService.getInventory(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(inventoryResponse);
    }
}