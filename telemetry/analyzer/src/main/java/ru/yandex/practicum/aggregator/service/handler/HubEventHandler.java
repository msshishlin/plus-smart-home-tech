package ru.yandex.practicum.aggregator.service.handler;

import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

/**
 * Контракт обработчика данных события в хабе.
 */
public interface HubEventHandler {
    /**
     * Получить тип события в хабе.
     *
     * @return тип события в хабе.
     */
    Class<?> getHubEventType();

    /**
     * Обработать данные о событии в хабе.
     *
     * @param hubEvent данные о событии в хабе.
     */
    void handle(HubEventAvro hubEvent);
}