package com.online.store.service;

import com.online.store.dto.InventoryRequest;
import com.online.store.dto.InventoryResponse;
import com.online.store.entity.Inventory;
import com.online.store.repository.InventoryRepository;
import com.online.store.enums.Category;
import com.online.store.enums.SubCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public void addInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = Inventory.builder()
                .productId(inventoryRequest.productId())
                .quantity(inventoryRequest.quantity())
                .category(inventoryRequest.category())
                .subCategory(inventoryRequest.subCategory())
                .build();
        inventoryRepository.save(inventory);
    }

    @Override
    public InventoryResponse getInventory(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for productId: " + productId));
        return new InventoryResponse(
                inventory.getProductId(),
                inventory.getQuantity(),
                inventory.getCategory(),
                inventory.getSubCategory()
        );
    }
}