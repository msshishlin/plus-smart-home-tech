package ru.yandex.practicum.warehouse.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

/**
 * Размеры товара.
 */
@Builder(toBuilder = true)
@Data
public class DimensionDto {
    /**
     * Ширина.
     */
    @NotNull
    @PositiveOrZero
    private Float width;

    /**
     * Высота.
     */
    @NotNull
    @PositiveOrZero
    private Float height;

    /**
     * Глубина.
     */
    @NotNull
    @PositiveOrZero
    private Float depth;
}
