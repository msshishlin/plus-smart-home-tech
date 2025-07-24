package ru.yandex.practicum.interactionapi.dto.order;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

/**
 * Представление заказа в системе.
 */
@Builder(toBuilder = true)
@Data
public class OrderDto {
    /**
     * Идентификатор заказа.
     */
    private UUID orderId;

    /**
     * Идентификатор корзины товаров.
     */
    private UUID shoppingCartId;

    /**
     * Отображение идентификатора товара на отобранное количество.
     */
    private Map<UUID, Integer> products;

    /**
     * Идентификатор оплаты.
     */
    private UUID paymentId;

    /**
     * Идентификатор доставки.
     */
    private UUID deliveryId;

    /**
     * Статус заказа.
     */
    private OrderState state;

    /**
     * Общий вес доставки.
     */
    private Float deliveryWeight;

    /**
     * Общий объем доставки.
     */
    private Float deliveryVolume;

    /**
     * Признак хрупкости заказа.
     */
    private Boolean fragile;

    /**
     * Общая стоимость.
     */
    private Double totalPrice;

    /**
     * Стоимость доставки.
     */
    private Double deliveryPrice;

    /**
     * Стоимость товаров в заказе.
     */
    private Double productPrice;
}
