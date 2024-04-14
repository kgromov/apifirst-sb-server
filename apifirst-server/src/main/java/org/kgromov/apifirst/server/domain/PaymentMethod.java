package org.kgromov.apifirst.server.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Delegate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @NotNull
    @Size(min = 2, max = 100)
    private String displayName;
    @NotNull
    private Integer cardNumber;
    @NotNull
    private Integer expiryMonth;
    @NotNull
    private Integer expiryYear;
    @NotNull
    private Integer cvv;

    @Delegate
    @Embedded
    private TimestampAudited timestampAudited;

    @ManyToOne
    private Customer customer;
}
