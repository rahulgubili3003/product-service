package com.online.store.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.store.exception.JsonResponseCreationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.online.store.common.ResultInfoErrorConstants.JSON_STRING_CONVERSION_FAILED;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonResponseUtil {
    public static String createJsonString(final Object response) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse response object to Json String", e);
            throw new JsonResponseCreationException(JSON_STRING_CONVERSION_FAILED);
        }
    }
}
