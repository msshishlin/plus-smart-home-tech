package ru.yandex.practicum.interactionapi.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Запрос на передачу в доставку товаров.
 */
@AllArgsConstructor
@Data
public class ShippedToDeliveryRequest {
    /**
     * Идентификатор заказа в БД.
     */
    @NotNull
    private UUID orderId;

    /**
     * Идентификатор доставки в БД.
     */
    @NotNull
    private UUID deliveryId;
}
