package org.kgromov.apifirst.server.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "char(36)", updatable = false, nullable = false)
    private UUID id;

    private String displayName;
    private Integer cardNumber;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Integer cvv;

    @CreationTimestamp
    private OffsetDateTime created;

    @UpdateTimestamp
    private OffsetDateTime modified;

    @ManyToOne
    private Customer customer;
}
