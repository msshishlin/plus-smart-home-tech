package ru.yandex.practicum.collector.service.handler;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

/**
 * Контракт обработчика данных события в хабе.
 */
public interface HubEventHandler {
    /**
     * Получить тип события в хабе.
     *
     * @return тип события в хабе.
     */
    HubEventProto.PayloadCase getHubEventType();

    /**
     * Обработать данные о событии в хабе.
     *
     * @param hubEvent данные о событии в хабе.
     */
    void handle(HubEventProto hubEvent);
}
