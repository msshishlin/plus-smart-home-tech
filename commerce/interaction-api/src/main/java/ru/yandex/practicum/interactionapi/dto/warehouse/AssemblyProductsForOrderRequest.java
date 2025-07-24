package ru.yandex.practicum.interactionapi.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

/**
 * Запрос на сбор заказа из товаров.
 */
@Builder(toBuilder = true)
@Data
public class AssemblyProductsForOrderRequest {
    /**
     * Отображение идентификатора товара на отобранное количество.
     */
    @NotNull
    private Map<UUID, Integer> products;

    /**
     * Идентификатор заказа в БД.
     */
    @NotNull
    private UUID orderId;
}
