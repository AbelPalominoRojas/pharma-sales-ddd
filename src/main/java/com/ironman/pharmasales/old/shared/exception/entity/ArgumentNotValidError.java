package com.ironman.pharmasales.old.shared.exception.entity;

import lombok.Data;

import java.util.Map;

@Data
public class ArgumentNotValidError {
    private String message;
    private Map<String, String> error;
}
