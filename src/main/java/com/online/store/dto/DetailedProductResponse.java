package com.online.store.dto;

import com.online.store.enums.Category;
import com.online.store.enums.Label;
import com.online.store.enums.SubCategory;
import lombok.Builder;

@Builder
public record DetailedProductResponse(
        Long productId,
        String productName,
        String productDescription,
        Integer fullPrice,
        Integer priceWithoutTax,
        String image,
        Category category,
        SubCategory subCategory,
        Label label
) {
}
