package org.kgromov.apifirst.server.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Delegate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.util.CollectionUtils;

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

    private String email;
    private String phone;
    @Delegate
    @Embedded
    private Name name;

    @Delegate
    @Embedded
    private TimestampAudited timestampAudited;

    @OneToOne(cascade = CascadeType.ALL)
    private Address shipToAddress;
    @OneToOne(cascade = CascadeType.ALL)
    private Address billToAddress;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentMethod> paymentMethods;

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
        if (!CollectionUtils.isEmpty(this.paymentMethods)) {
            paymentMethods.forEach(pm -> pm.setCustomer(this));
        }
    }

    /*
     * Alternative solution with hibernate lifecycle hook:
     *  @PrePersist
        public void prePersist() {
            if (this.paymentMethods != null && !this.paymentMethods.isEmpty()) {
                this.paymentMethods.forEach(paymentMethod -> paymentMethod.setCustomer(this));
            }
        }
     */
}
