package ru.yandex.practicum.interactionapi.exception.delivery;

import java.util.UUID;

/**
 * Исключение, выбрасываемое сервисом, если доставка не была найдена.
 */
public class NoDeliveryFoundException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param deliveryId идентификатор доставки.
     */
    public NoDeliveryFoundException(UUID deliveryId) {
        super(String.format("Доставка с id=%s не найдена", deliveryId));
    }
}
