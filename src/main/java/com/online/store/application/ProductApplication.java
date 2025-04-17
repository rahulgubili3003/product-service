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

    public List<DetailedProductResponse> getProducts(@NonNull final String sortBy) {
        return productsRepository.findAll(Sort.by(sortBy)).stream().map(this::buildDetailedResponse).toList();
    }

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

    public ProductResponse addProducts(final ProductRequest productRequest) throws Exception {
        if (productRequest == null) {
            throw new Exception("productRequest cannot be null");
        }
        final Products product = Products.builder()
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
        // Save Product in the DB
        final var savedProduct = productsRepository.save(product);
        // Prepare the Response Object
        return ProductResponse.builder()
                .productId(null)
                .productName(savedProduct.getProductName())
                .fullPrice(null)
                .build();
    }
}
