package com.ironman.pharmasales.shared.domain.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class PageableRequest {
    private int page;
    private int size;
    private String sort;
    private String direction;
}
