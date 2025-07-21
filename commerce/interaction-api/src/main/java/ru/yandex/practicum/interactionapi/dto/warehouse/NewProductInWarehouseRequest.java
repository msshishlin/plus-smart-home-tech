package ru.yandex.practicum.interactionapi.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Запрос на добавление нового товара на склад.
 */
@Builder(toBuilder = true)
@Data
public class NewProductInWarehouseRequest {
    /**
     * Идентификатор товара в БД.
     */
    @NotNull
    private UUID productId;

    /**
     * Признак хрупкости.
     */
    @NotNull
    private Boolean fragile;

    /**
     * Размеры товара.
     */
    @NotNull
    private DimensionDto dimension;

    /**
     * Вес товара.
     */
    @NotNull
    @PositiveOrZero
    private Float weight;
}
