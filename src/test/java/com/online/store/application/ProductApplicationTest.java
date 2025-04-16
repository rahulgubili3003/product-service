package com.online.store.application;

import com.online.store.dto.DetailedProductResponse;
import com.online.store.dto.ProductRequest;
import com.online.store.dto.ProductResponse;
import com.online.store.entity.Products;
import com.online.store.enums.Category;
import com.online.store.enums.Label;
import com.online.store.enums.SubCategory;
import com.online.store.repository.ProductsRepository;
import com.online.store.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductApplicationTest {

    @Mock
    private ProductsRepository productsRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductApplication productApplication;

    private Products testProduct;
    private ProductRequest testProductRequest;

    @BeforeEach
    void setUp() {
        // Setup test product entity
        testProduct = Products.builder()
                .id(1L)
                .productId(1001L)
                .productName("Test Basmati Rice")
                .productDescription("Premium quality basmati rice")
                .fullPrice(599)
                .priceWithoutTax(499)
                .category(Category.RICE)
                .subCategory(SubCategory.BASMATI)
                .label(Label.VEGETARIAN)
                .image("rice.jpg")
                .build();

        // Setup test product request
        testProductRequest = new ProductRequest(
                1001L,
                "Test Basmati Rice",
                "Premium quality basmati rice",
                599,
                499,
                Category.RICE,
                SubCategory.BASMATI,
                Label.VEGETARIAN,
                "rice.jpg"
        );
    }

    @Test
    void getProducts_ShouldReturnSortedProducts() {
        // Arrange
        List<Products> productsList = List.of(testProduct);
        when(productsRepository.findAll(any(Sort.class))).thenReturn(productsList);

        // Act
        List<DetailedProductResponse> result = productApplication.getProducts("productName");

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        verify(productsRepository).findAll(Sort.by("productName"));

        DetailedProductResponse response = result.get(0);
        assertEquals(testProduct.getProductId(), response.productId());
        assertEquals(testProduct.getProductName(), response.productName());
        assertEquals(testProduct.getProductDescription(), response.productDescription());
        assertEquals(testProduct.getFullPrice(), response.fullPrice());
        assertEquals(testProduct.getPriceWithoutTax(), response.priceWithoutTax());
        assertEquals(testProduct.getCategory(), response.category());
        assertEquals(testProduct.getSubCategory(), response.subCategory());
        assertEquals(testProduct.getLabel(), response.label());
        assertEquals(testProduct.getImage(), response.image());
    }

    @Test
    void getProducts_WithEmptyRepository_ShouldReturnEmptyList() {
        // Arrange
        when(productsRepository.findAll(any(Sort.class))).thenReturn(Collections.emptyList());

        // Act
        List<DetailedProductResponse> result = productApplication.getProducts("productName");

        // Assert
        assertThat(result).isEmpty();
        verify(productsRepository).findAll(any(Sort.class));
    }

    @Test
    void getProducts_WithMultipleProducts_ShouldReturnAllMapped() {
        // Arrange
        Products secondProduct = Products.builder()
                .id(2L)
                .productId(1002L)
                .productName("Turmeric Powder")
                .productDescription("Organic turmeric powder")
                .fullPrice(299)
                .priceWithoutTax(249)
                .category(Category.SPICE)
                .subCategory(SubCategory.WHOLE_SPICES)
                .label(Label.VEGAN)
                .image("turmeric.jpg")
                .build();

        List<Products> productsList = List.of(testProduct, secondProduct);
        when(productsRepository.findAll(any(Sort.class))).thenReturn(productsList);

        // Act
        List<DetailedProductResponse> result = productApplication.getProducts("category");

        // Assert
        assertThat(result).hasSize(2);
        verify(productsRepository).findAll(Sort.by("category"));

        // Verify first product mapping
        DetailedProductResponse firstResponse = result.get(0);
        assertEquals(testProduct.getProductId(), firstResponse.productId());

        // Verify second product mapping
        DetailedProductResponse secondResponse = result.get(1);
        assertEquals(secondProduct.getProductId(), secondResponse.productId());
        assertEquals(secondProduct.getProductName(), secondResponse.productName());
    }

    @Test
    void addProducts_ShouldSaveProductAndReturnResponse() {
        // Arrange
        when(productsRepository.save(any(Products.class))).thenReturn(testProduct);

        // Act
        ProductResponse result = productApplication.addProducts(testProductRequest);

        // Assert
        assertNotNull(result);
        assertEquals(testProduct.getProductId(), result.productId());
        assertEquals(testProduct.getProductName(), result.productName());
        assertEquals(testProduct.getFullPrice(), result.fullPrice());

        // Verify repository interaction
        verify(productsRepository).save(any(Products.class));
    }

    @Test
    void addProducts_ShouldMapRequestToEntityCorrectly() {
        // Arrange
        ArgumentCaptor<Products> productsCaptor = ArgumentCaptor.forClass(Products.class);
        when(productsRepository.save(any(Products.class))).thenReturn(testProduct);

        // Act
        productApplication.addProducts(testProductRequest);

        // Assert - verify the request is mapped correctly to a Products entity
        verify(productsRepository).save(productsCaptor.capture());
        Products capturedProduct = productsCaptor.getValue();

        assertEquals(testProductRequest.productId(), capturedProduct.getProductId());
        assertEquals(testProductRequest.productName(), capturedProduct.getProductName());
        assertEquals(testProductRequest.description(), capturedProduct.getProductDescription());
        assertEquals(testProductRequest.fullPrice(), capturedProduct.getFullPrice());
        assertEquals(testProductRequest.priceWithoutTax(), capturedProduct.getPriceWithoutTax());
        assertEquals(testProductRequest.category(), capturedProduct.getCategory());
        assertEquals(testProductRequest.subCategory(), capturedProduct.getSubCategory());
        assertEquals(testProductRequest.label(), capturedProduct.getLabel());
        assertEquals(testProductRequest.image(), capturedProduct.getImage());
    }

    @Test
    void getProducts_WithNullSortBy_ShouldThrowNullPointerException() {
        // Act & Assert: Passing a null sortBy should throw an exception and avoid any repository interaction
        assertThrows(NullPointerException.class, () -> productApplication.getProducts(null));
        verifyNoInteractions(productsRepository);
    }

    @Test
    void addProducts_WithNullRequest_ShouldHandleGracefully() {
        // Act & Assert: Passing a null productRequest should throw an exception and avoid repository interaction
        assertThrows(NullPointerException.class, () -> productApplication.addProducts(null));
        verifyNoInteractions(productsRepository);
    }

    @Test
    void buildDetailedResponse_ShouldMapAllFields() {
        // This test verifies the private buildDetailedResponse method via getProducts
        when(productsRepository.findAll(any(Sort.class))).thenReturn(List.of(testProduct));

        List<DetailedProductResponse> result = productApplication.getProducts("id");

        DetailedProductResponse response = result.get(0);
        // Verify that all fields are correctly mapped
        assertEquals(testProduct.getProductId(), response.productId());
        assertEquals(testProduct.getProductName(), response.productName());
        assertEquals(testProduct.getProductDescription(), response.productDescription());
        assertEquals(testProduct.getFullPrice(), response.fullPrice());
        assertEquals(testProduct.getPriceWithoutTax(), response.priceWithoutTax());
        assertEquals(testProduct.getCategory(), response.category());
        assertEquals(testProduct.getSubCategory(), response.subCategory());
        assertEquals(testProduct.getLabel(), response.label());
        assertEquals(testProduct.getImage(), response.image());
    }

    @Test
    void mapToProducts_ShouldMapAllFields() {
        // This test verifies the private mapToProducts method via addProducts
        ArgumentCaptor<Products> productsCaptor = ArgumentCaptor.forClass(Products.class);
        when(productsRepository.save(any(Products.class))).thenReturn(testProduct);

        productApplication.addProducts(testProductRequest);

        verify(productsRepository).save(productsCaptor.capture());
        Products capturedProduct = productsCaptor.getValue();

        // Verify that all fields are mapped correctly from ProductRequest to Products
        assertEquals(testProductRequest.productId(), capturedProduct.getProductId());
        assertEquals(testProductRequest.productName(), capturedProduct.getProductName());
        assertEquals(testProductRequest.description(), capturedProduct.getProductDescription());
        assertEquals(testProductRequest.fullPrice(), capturedProduct.getFullPrice());
        assertEquals(testProductRequest.priceWithoutTax(), capturedProduct.getPriceWithoutTax());
        assertEquals(testProductRequest.category(), capturedProduct.getCategory());
        assertEquals(testProductRequest.subCategory(), capturedProduct.getSubCategory());
        assertEquals(testProductRequest.label(), capturedProduct.getLabel());
        assertEquals(testProductRequest.image(), capturedProduct.getImage());
    }

    @Test
    void mapToProductResponse_ShouldMapRequiredFields() {
        // This test verifies the mapping from Products to ProductResponse via addProducts
        when(productsRepository.save(any(Products.class))).thenReturn(testProduct);

        ProductResponse result = productApplication.addProducts(testProductRequest);

        assertNotNull(result);
        assertEquals(testProduct.getProductId(), result.productId());
        assertEquals(testProduct.getProductName(), result.productName());
        assertEquals(testProduct.getFullPrice(), result.fullPrice());
    }
}
