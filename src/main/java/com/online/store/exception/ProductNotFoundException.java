package com.online.store.exception;

import com.online.store.common.ResultInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(force = true)
public class ProductNotFoundException extends RuntimeException {
    private final ResultInfo resultInfo;

    public ProductNotFoundException(final ResultInfo resultInfo) {
        super(resultInfo.message());
        this.resultInfo = resultInfo;
    }
}
