package com.online.store.dto;

import lombok.Builder;

@Builder
public record StockResponse(Integer noOfItems) {
}
