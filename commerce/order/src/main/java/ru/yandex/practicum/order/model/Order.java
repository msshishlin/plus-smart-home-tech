package ru.yandex.practicum.order.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import ru.yandex.practicum.interactionapi.dto.order.OrderState;

import java.util.Map;
import java.util.UUID;

/**
 * Заказ.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "orders")
@ToString
public class Order {
    /**
     * Идентификатор заказа.
     */
    @Column(name = "order_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID orderId;

    /**
     * Идентификатор корзины товаров.
     */
    @Column(name = "shopping_cart_id", nullable = false)
    private UUID shoppingCartId;

    /**
     * Отображение идентификатора товара на отобранное количество.
     */
    @CollectionTable(name = "products", joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "order_id")})
    @Column(name = "quantity", nullable = false)
    @ElementCollection
    @MapKeyColumn(name = "product_id")
    private Map<UUID, Integer> products;

    /**
     * Идентификатор оплаты.
     */
    @Column(name = "payment_id", nullable = false)
    private UUID paymentId;

    /**
     * Идентификатор доставки.
     */
    @Column(name = "delivery_id", nullable = false)
    private UUID deliveryId;

    /**
     * Статус заказа.
     */
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcType(value = PostgreSQLEnumJdbcType.class)
    private OrderState state;

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
     * Общая стоимость.
     */
    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    /**
     * Стоимость доставки.
     */
    @Column(name = "delivery_price", nullable = false)
    private double deliveryPrice;

    /**
     * Стоимость товаров в заказе.
     */
    @Column(name = "product_price", nullable = false)
    private double productPrice;
}
