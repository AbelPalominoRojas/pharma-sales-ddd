package com.ironman.pharmasales.users.infrastructure.persistence.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Optional.ofNullable;

@Getter
@RequiredArgsConstructor
public enum UserSortField {
    ID("id", "id"),
    NAME("name", "name"),
    LAST_NAME("lastName", "last_name"),
    PROFILE_ID("profileId", "profile_id"),
    CREATED_AT("createdAt", "created_at");

    private final String value;
    private final String column;

    public static String getSqlName(String name) {
        try {
            return UserSortField.valueOf(name).getColumn();
        } catch (Exception e) {
            return ofNullable(name).orElseGet(ID::getColumn);
        }
    }
}
