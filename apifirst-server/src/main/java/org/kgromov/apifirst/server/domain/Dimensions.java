package org.kgromov.apifirst.server.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Embeddable
public class Dimensions {
    private int length;
    private int width;
    private int height;
}
