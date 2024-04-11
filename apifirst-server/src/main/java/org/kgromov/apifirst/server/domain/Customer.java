package org.kgromov.apifirst.server.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "char(36)", updatable = false, nullable = false)
    private UUID id;

    @OneToOne
    private Address shipToAddress;

    @OneToOne
    private Address billToAddress;

    @Embedded
    private Name name;

    private String email;
    private String phone;

    @OneToMany(mappedBy = "customer")
    private List<PaymentMethod> paymentMethods;

    @CreationTimestamp
    private OffsetDateTime created;
    @UpdateTimestamp
    private OffsetDateTime modified;
}
