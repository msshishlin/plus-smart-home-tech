package ru.yandex.practicum.payment.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.UUID;

/**
 * Информация об оплате.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "payments")
@ToString
public class Payment {
    /**
     * Идентификатор оплаты.
     */
    @Column(name = "payment_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID paymentId;

    /**
     * Идентификатор заказа.
     */
    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    /**
     * Общая стоимость.
     */
    @Column(name = "total_payment", nullable = false)
    private double totalPayment;

    /**
     * Стоимость доставки.
     */
    @Column(name = "delivery_total", nullable = false)
    private double deliveryTotal;

    /**
     * Стоимость налога.
     */
    @Column(name = "fee_total", nullable = false)
    private double feeTotal;

    /**
     * Статус платежа.
     */
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcType(value = PostgreSQLEnumJdbcType.class)
    private PaymentState state;
}
