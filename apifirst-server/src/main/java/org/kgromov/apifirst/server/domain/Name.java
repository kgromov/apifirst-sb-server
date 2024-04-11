package org.kgromov.apifirst.server.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Embeddable
public class Name {
    private String prefix;
    private String firstName;
    private String lastName;
    private String suffix;
}
