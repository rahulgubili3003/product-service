package com.online.store.application;

import com.online.store.dto.DetailedProductResponse;
import com.online.store.dto.ProductRequest;
import com.online.store.dto.ProductResponse;
import com.online.store.entity.Products;
import com.online.store.repository.ProductsRepository;
import com.online.store.repository.StockRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductApplication {

    private final ProductsRepository productsRepository;
    private final StockRepository stockRepository;

    /**
     * Retrieves a list of products sorted by the specified field.
     *
     * @param sortBy the field to sort by
     * @return a list of detailed product responses
     */
    public List<DetailedProductResponse> getProducts(@NonNull final String sortBy) {
        return productsRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(this::buildDetailedResponse)
                .toList();
    }

    /**
     * Adds a new product to the database.
     *
     * @param productRequest the product request containing product details
     * @return the response containing saved product details
     */
    public ProductResponse addProducts(final ProductRequest productRequest) {
        final Products product = mapToProducts(productRequest);
        final var savedProduct = productsRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    /**
     * Builds a detailed product response from a product entity.
     *
     * @param products the product entity
     * @return the detailed product response
     */
    private DetailedProductResponse buildDetailedResponse(@NonNull final Products products) {
        return DetailedProductResponse.builder()
                .productId(products.getProductId())
                .productName(products.getProductName())
                .productDescription(products.getProductDescription())
                .category(products.getCategory())
                .subCategory(products.getSubCategory())
                .fullPrice(products.getFullPrice())
                .label(products.getLabel())
                .priceWithoutTax(products.getPriceWithoutTax())
                .image(products.getImage())
                .build();
    }

    /**
     * Maps a product request to a product entity.
     *
     * @param productRequest the product request
     * @return the product entity
     */
    private Products mapToProducts(final ProductRequest productRequest) {
        return Products.builder()
                .productId(productRequest.productId())
                .productName(productRequest.productName())
                .productDescription(productRequest.description())
                .fullPrice(productRequest.fullPrice())
                .priceWithoutTax(productRequest.priceWithoutTax())
                .category(productRequest.category())
                .subCategory(productRequest.subCategory())
                .label(productRequest.label())
                .image(productRequest.image())
                .build();
    }

    /**
     * Maps a product entity to a product response.
     *
     * @param savedProduct the saved product entity
     * @return the product response
     */
    private ProductResponse mapToProductResponse(final Products savedProduct) {
        return ProductResponse.builder()
                .productId(savedProduct.getProductId())
                .productName(savedProduct.getProductName())
                .fullPrice(savedProduct.getFullPrice())
                .build();
    }
}