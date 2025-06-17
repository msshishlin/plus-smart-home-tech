package ru.yandex.practicum.analyzer.service.handler;

import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public interface SensorsSnapshotHandler {
    void handle(SensorsSnapshotAvro sensorsSnapshot);
}
