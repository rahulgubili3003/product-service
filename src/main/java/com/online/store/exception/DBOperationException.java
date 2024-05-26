package com.online.store.exception;

import com.online.store.common.ResultInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class DBOperationException extends RuntimeException {
    private final ResultInfo resultInfo;

    public DBOperationException(ResultInfo resultInfo) {
        super(resultInfo.message());
        this.resultInfo = resultInfo;
    }
}
