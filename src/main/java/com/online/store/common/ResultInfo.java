package com.online.store.common;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public record ResultInfo(String code, String message, HttpStatus status) implements Serializable {
}
