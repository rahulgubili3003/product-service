package com.online.store.controller;

import com.online.store.application.ProductApplication;
import com.online.store.dto.ProductRequest;
import com.online.store.util.JsonResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductApplication productApplication;

    @PostMapping("/addProducts")
    public ResponseEntity<String> addProducts(@Validated @RequestBody final ProductRequest productRequest) {
        final var savedProduct = productApplication.addProducts(productRequest);
        final var jsonString = JsonResponseUtil.createJsonString(savedProduct);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonString);
    }

    @GetMapping("/displayProducts")
    public ResponseEntity<String> getProducts(@RequestParam(defaultValue = "productName") final String sortBy) {
        final var allProducts = productApplication.getProducts(sortBy);
        final String jsonString = JsonResponseUtil.createJsonString(allProducts);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(jsonString);
    }
}
