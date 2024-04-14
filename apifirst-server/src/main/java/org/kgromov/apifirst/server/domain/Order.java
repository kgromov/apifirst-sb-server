package org.kgromov.apifirst.server.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "char(36)", updatable = false, nullable = false)
    private UUID id;
    @Size(min = 1, max = 255)
    private String shipmentInfo;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus orderStatus = OrderStatus.NEW;

    @Delegate
    @Embedded
    private TimestampAudited timestampAudited;

    @ManyToOne
    @NotNull
    private Customer customer;
    @ManyToOne
    private PaymentMethod selectedPaymentMethod;
    @NotNull
    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines = new ArrayList<>();
    @PrePersist
    public void prePersist() {
        this.orderLines.forEach(paymentMethod -> paymentMethod.setOrder(this));
    }
}
