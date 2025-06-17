package ru.yandex.practicum.analyzer.service.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;

@Component
@RequiredArgsConstructor
public class DeviceRemovedEventHandler extends BaseHubEventHandler<DeviceRemovedEventAvro> {
    /**
     * Хранилище датчиков.
     */
    private final SensorRepository sensorRepository;

    @Override
    protected void handle(String hubId, DeviceRemovedEventAvro hubEvent) {
        sensorRepository.findByIdAndHubId(hubEvent.getId(), hubId).ifPresent(sensorRepository::delete);
    }
}
