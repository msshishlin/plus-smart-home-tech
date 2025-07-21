package ru.yandex.practicum.interactionapi.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

/**
 * Запрос на увеличение единиц товара по его идентификатору.
 */
@Data
public class AddProductToWarehouseRequest {
    /**
     * Идентификатор товара в БД.
     */
    @NotNull
    private UUID productId;

    /**
     * Количество единиц товара для добавления на склад.
     */
    @NotNull
    @Positive
    private Integer quantity;
}
