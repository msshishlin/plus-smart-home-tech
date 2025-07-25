package ru.yandex.practicum.delivery.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import ru.yandex.practicum.interactionapi.dto.delivery.DeliveryState;

import java.util.UUID;

/**
 * Доставка.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "deliveries")
@ToString
public class Delivery {
    /**
     * Идентификатор доставки.
     */
    @Column(name = "delivery_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID deliveryId;

    /**
     * Адрес отправки.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "from_address_id", referencedColumnName = "address_id")
    private Address fromAddress;

    /**
     * Адрес получения.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "to_address_id", referencedColumnName = "address_id")
    private Address toAddress;

    /**
     * Статус доставки.
     */
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcType(value = PostgreSQLEnumJdbcType.class)
    private DeliveryState state;

    /**
     * Идентификатор заказа.
     */
    @Column(name = "order_id", nullable = false)
    private UUID orderId;
}
