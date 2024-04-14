package org.kgromov.apifirst.server.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "char(36)", updatable = false, nullable = false)
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @Column(unique = true)
    @NotNull
    @Size(min = 3, max = 25)
    private String code;

    @Delegate
    @Embedded
    private TimestampAudited timestampAudited;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products;
}



