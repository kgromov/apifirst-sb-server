package org.kgromov.apifirst.server.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Delegate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
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
    @NotNull
    @Size(min = 5, max = 255)
    private String description;
    @NotNull
    @Pattern(regexp = "^-?(?:0|[1-9]\\d{0,2}(?:,?\\d{3})*)(?:\\.\\d+)?$")
    private String price;
    @Pattern(regexp = "^-?(?:0|[1-9]\\d{0,2}(?:,?\\d{3})*)(?:\\.\\d+)?$")
    private String cost;
    @Delegate
    @Embedded
    private Dimensions dimensions;

    @Delegate
    @Embedded
    private TimestampAudited timestampAudited;

    @OneToMany(mappedBy = "product")
    private List<Image> images = new ArrayList<>();
    @ManyToMany
    private List<Category> categories;

 /*   @PreUpdate
    @PrePersist
    public void prePersist() {
        if (this.images != null && !this.images.isEmpty()) {
            this.images.forEach(image -> image.setProduct(this));
        }
    }*/

    // Alternative approach - override lombok builder build method
    public static class ProductBuilder {
        public Product build() {
            Product product = new Product(this.id, this.description, this.price, this.cost, this.dimensions, this.timestampAudited, this.images, this.categories);
            if (this.images != null) {
                this.images.forEach(i -> i.setProduct(product));
            }
            return product;
        }
    }
}
