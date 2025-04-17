package com.online.store.application;

import com.online.store.dto.StockRequest;
import com.online.store.dto.StockResponse;
import com.online.store.entity.Products;
import com.online.store.entity.Stock;
import com.online.store.exception.DBOperationException;
import com.online.store.exception.ProductNotFoundException;
import com.online.store.repository.ProductsRepository;
import com.online.store.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import static com.online.store.common.ResultInfoErrorConstants.PRODUCT_NOT_FOUND;
import static com.online.store.common.ResultInfoErrorConstants.SAVING_IN_DB_FAILURE;

@Service
@RequiredArgsConstructor
public class StockApplication {

    private final StockRepository stockRepository;
    private final ProductsRepository productsRepository;

    public StockResponse addStockDetails(final StockRequest stockRequest) {
        final Products product = productsRepository.
                findProductsByProductId(
                        stockRequest.productId())
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        final Stock stock = Stock.builder()
                .productId(product)
                .noOfItems(stockRequest.noOfItems())
                .build();

        try {
            stockRepository.save(stock);
            return StockResponse.builder()
                    .noOfItems(stockRequest.noOfItems())
                    .build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DBOperationException(SAVING_IN_DB_FAILURE);
        }
    }
}