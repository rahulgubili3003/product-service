package com.online.store.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.store.enums.Category;
import com.online.store.enums.Label;
import com.online.store.enums.SubCategory;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductRequest(
        Long productId,
        String productName,
        String description,
        Integer fullPrice,
        Integer priceWithoutTax,
        Category category,
        SubCategory subCategory,
        Label label,
        String image
) {
}
