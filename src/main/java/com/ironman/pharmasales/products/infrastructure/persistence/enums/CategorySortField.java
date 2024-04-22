package com.ironman.pharmasales.products.infrastructure.persistence.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Optional.ofNullable;

@Getter
@RequiredArgsConstructor
public enum CategorySortField {
    ID("id", "id"),
    NAME("name", "name"),
    CREATED_AT("createdAt", "created_at");

    private final String value;
    private final String column;

    public static String getSqlName(String name) {
        try {
            return CategorySortField.valueOf(name).getColumn();
        } catch (Exception e) {
            return ofNullable(name).orElseGet(ID::getColumn);
        }
    }
}
