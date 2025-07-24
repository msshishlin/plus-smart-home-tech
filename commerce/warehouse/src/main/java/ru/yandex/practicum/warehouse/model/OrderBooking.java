package ru.yandex.practicum.warehouse.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;
import java.util.UUID;

/**
 * Забронированные для заказа товары.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "orders")
@ToString
public class OrderBooking {
    /**
     * Идентификатор заказа в БД.
     */
    @Column(name = "order_id", nullable = false)
    @Id
    private UUID orderId;

    /**
     * Идентификатор доставки в БД.
     */
    @Column(name = "delivery_id", nullable = true)
    private UUID deliveryId;

    /**
     * Общий вес доставки.
     */
    @Column(name = "delivery_weight", nullable = false)
    private float deliveryWeight;

    /**
     * Общий объем доставки.
     */
    @Column(name = "delivery_volume", nullable = false)
    private float deliveryVolume;

    /**
     * Признак хрупкости заказа.
     */
    @Column(name = "fragile", nullable = false)
    private boolean fragile;

    /**
     * Отображение идентификатора товара на забронированное количество.
     */
    @CollectionTable(name = "booked_products", joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "order_id")})
    @Column(name = "quantity", nullable = false)
    @ElementCollection
    @MapKeyColumn(name = "product_id")
    private Map<UUID, Integer> products;
}
