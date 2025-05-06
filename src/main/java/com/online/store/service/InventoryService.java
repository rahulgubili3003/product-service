package com.online.store.service;

import com.online.store.dto.InventoryRequest;
import com.online.store.dto.InventoryResponse;

public interface InventoryService {
    void addInventory(InventoryRequest inventoryRequest);
    InventoryResponse getInventory(Long productId);
}