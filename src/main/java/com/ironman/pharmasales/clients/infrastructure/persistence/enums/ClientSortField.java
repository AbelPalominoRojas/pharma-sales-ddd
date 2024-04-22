package com.ironman.pharmasales.clients.infrastructure.persistence.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Optional.ofNullable;

@Getter
@RequiredArgsConstructor
public enum ClientSortField {
    ID("id", "id"),
    NAME("name", "name"),
    LAST_NAME("lastName", "last_name"),
    DOCUMENT_TYPE_ID("documentTypeId", "document_type_id"),
    CREATED_AT("createdAt", "created_at");

    private final String value;
    private final String column;

    public static String getSqlName(String name) {
        try {
            return ClientSortField.valueOf(name).getColumn();
        } catch (Exception e) {
            return ofNullable(name).orElseGet(ID::getColumn);
        }
    }
}
