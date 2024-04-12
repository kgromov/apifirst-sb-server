package org.kgromov.apifirst.server.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Delegate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "char(36)", updatable = false, nullable = false)
    private UUID id;

    private String description;
    private String price;
    private String cost;
    @Delegate
    @Embedded
    private Dimensions dimensions;

    @Delegate
    @Embedded
    private TimestampAudited timestampAudited;

    @OneToMany(mappedBy = "product")
    private List<Image> images;
    @ManyToMany
    private List<Category> categories;
}
