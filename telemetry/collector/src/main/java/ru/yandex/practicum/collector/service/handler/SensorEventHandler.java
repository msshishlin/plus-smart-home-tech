package ru.yandex.practicum.collector.service.handler;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

/**
 * Контракт обработчика данных о событии датчика.
 */
public interface SensorEventHandler {
    /**
     * Получить тип датчика, на котором произошло событие.
     *
     * @return тип датчика, на котором произошло событие
     */
    SensorEventProto.PayloadCase getSensorEventType();

    /**
     * Обработать данные о событии датчика.
     *
     * @param sensorEvent данные о событии датчика.
     */
    void handle(SensorEventProto sensorEvent);
}
