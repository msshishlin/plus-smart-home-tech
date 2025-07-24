package ru.yandex.practicum.interactionapi.exception.order;

import java.util.UUID;

/**
 * Исключение, выбрасываемое сервисом, если заказ не был найден.
 */
public class NoOrderFoundException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param orderId идентификатор заказа.
     */
    public NoOrderFoundException(UUID orderId) {
        super(String.format("Заказ с id=%s не найден", orderId));
    }
}
