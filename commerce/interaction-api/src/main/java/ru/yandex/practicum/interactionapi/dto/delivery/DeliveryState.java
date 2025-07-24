package ru.yandex.practicum.interactionapi.dto.delivery;

/**
 * Статус доставки.
 */
public enum DeliveryState {
    /**
     * Создана.
     */
    CREATED,

    /**
     * В процессе.
     */
    IN_PROGRESS,

    /**
     * Доставлена.
     */
    DELIVERED,

    /**
     * Не доставлена.
     */
    FAILED,

    /**
     * Отменена.
     */
    CANCELLED
}
