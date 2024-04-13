package org.kgromov.apifirst.server.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryCode {
    ELECTRONICS("Electronics"),
    CLOTHING("Clothing"),
    DRYGOODS("Dry Goods");

    private final String name;
}
