package org.kgromov.apifirst.server.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Delegate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.net.URI;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "char(36)", updatable = false, nullable = false)
    private UUID id;

    private URI uri;
    private String altText;

    @Delegate
    @Embedded
    private TimestampAudited timestampAudited;

    @ManyToOne
    private Product product;
}