package ru.yandex.practicum.interactionapi.dto.order;

import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

/**
 * Запрос на возврат товаров.
 */
public class ProductReturnRequest {
    /**
     * Идентификатор заказа.
     */
    @NotNull
    private UUID orderId;

    /**
     * Отображение идентификатора товара на отобранное количество.
     */
    @NotNull
    private Map<UUID, Integer> products;
}
