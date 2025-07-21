package ru.yandex.practicum.interactionapi.dto.warehouse;

import lombok.Builder;
import lombok.Data;

/**
 * Общие сведения по бронированию.
 */
@Builder(toBuilder = true)
@Data
public class BookedProductsDto {
    /**
     * Общий вес доставки.
     */
    private float deliveryWeight;

    /**
     * Общие объём доставки.
     */
    private float deliveryVolume;

    /**
     * Есть ли хрупкие вещи в доставке.
     */
    private boolean fragile;
}
