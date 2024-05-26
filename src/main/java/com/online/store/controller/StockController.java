package com.online.store.controller;

import com.online.store.application.StockApplication;
import com.online.store.dto.StockRequest;
import com.online.store.util.JsonResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockApplication stockApplication;

    @PostMapping("/addStock")
    public ResponseEntity<String> addStock(@RequestBody final StockRequest stockRequest) {
        var addedStock = stockApplication.addStockDetails(stockRequest);
        final String jsonString = JsonResponseUtil.createJsonString(addedStock);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(jsonString);
    }
}
