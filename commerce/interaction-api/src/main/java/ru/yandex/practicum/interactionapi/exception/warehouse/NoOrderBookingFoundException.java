package ru.yandex.practicum.interactionapi.exception.warehouse;

import java.util.UUID;

/**
 * Исключение, выбрасываемое сервисом, если для заказа не были найдены забронированные товары.
 */
public class NoOrderBookingFoundException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param orderId идентификатор заказа в БД.
     */
    public NoOrderBookingFoundException(UUID orderId) {
        super(String.format("Забронированные товары для заказа с id=%s не найдены", orderId));
    }
}
