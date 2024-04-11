package org.kgromov.apifirst.server.domain;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Embeddable
public class TimestampAudited {
    @CreationTimestamp
    private OffsetDateTime created;
    @UpdateTimestamp
    private OffsetDateTime modified;
}
