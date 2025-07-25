package ru.yandex.practicum.interactionapi.dto.delivery;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.interactionapi.dto.AddressDto;

import java.util.UUID;

/**
 * Доставка.
 */
@Builder(toBuilder = true)
@Data
public class DeliveryDto {
    /**
     * Идентификатор доставки.
     */
    private UUID deliveryId;

    /**
     * Адрес отправки.
     */
    private AddressDto fromAddress;

    /**
     * Адрес получения.
     */
    private AddressDto toAddress;

    /**
     * Идентификатор заказа.
     */
    private UUID orderId;
}
