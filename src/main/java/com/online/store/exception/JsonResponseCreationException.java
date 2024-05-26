package com.online.store.exception;

import com.online.store.common.ResultInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
public class JsonResponseCreationException extends RuntimeException {
    private final ResultInfo resultInfo;

    public JsonResponseCreationException(final ResultInfo resultInfo) {
        super(resultInfo.message());
        this.resultInfo = resultInfo;
    }
}
