package com.online.store.service;

import com.online.store.dto.InventoryRequest;
import com.online.store.dto.InventoryResponse;
import com.online.store.entity.Inventory;
import com.online.store.entity.Products;
import com.online.store.exception.ProductNotFoundException;
import com.online.store.repository.InventoryRepository;
import com.online.store.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.online.store.common.ResultInfoErrorConstants.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductsRepository productsRepository;

    public InventoryResponse addInventory(final InventoryRequest inventoryRequest) {
        // Validate product existence
        final Products product = productsRepository.findProductsByProductId(inventoryRequest.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        // Create and save inventory
        final Inventory inventory = Inventory.builder()
                .product(product)
                .quantity(inventoryRequest.getQuantity())
                .build();
        inventoryRepository.save(inventory);

        // Return response
        return InventoryResponse.builder()
                .productId(product.getProductId())
                .quantity(inventory.getQuantity())
                .build();
    }

    public InventoryResponse getInventory(final Long productId) {
        // Validate product existence
        final Products product = productsRepository.findProductsByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        // Retrieve inventory
        final Inventory inventory = inventoryRepository.findByProduct(product)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        // Return response
        return InventoryResponse.builder()
                .productId(product.getProductId())
                .quantity(inventory.getQuantity())
                .build();
    }
}