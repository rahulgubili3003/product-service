package com.online.store.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultInfoErrorConstants {
    public static final ResultInfo JSON_STRING_CONVERSION_FAILED = new ResultInfo("001", "Parsing Failed. Could not generate response", HttpStatus.INTERNAL_SERVER_ERROR);
    public static final ResultInfo PRODUCT_NOT_FOUND = new ResultInfo("002", "Product Not Found in the DB", HttpStatus.BAD_REQUEST);
    public static final ResultInfo SAVING_IN_DB_FAILURE = new ResultInfo("003", "Could not save entity in DB", HttpStatus.BAD_REQUEST);
}
