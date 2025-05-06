package com.online.store.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.store.enums.Category;
import com.online.store.enums.SubCategory;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record InventoryResponse(
        Long productId,
        Integer quantity,
        Category category,
        SubCategory subCategory
) {
}