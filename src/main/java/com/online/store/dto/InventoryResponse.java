package com.online.store.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryResponse {
    private Long productId;
    private Integer quantity;

    public String toJson() {
        return String.format("{\"productId\":%d,\"quantity\":%d}", productId, quantity);
    }
}