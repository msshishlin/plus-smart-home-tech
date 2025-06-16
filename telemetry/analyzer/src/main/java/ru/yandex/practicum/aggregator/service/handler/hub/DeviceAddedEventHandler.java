package ru.yandex.practicum.aggregator.service.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.aggregator.model.Sensor;
import ru.yandex.practicum.aggregator.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;

@Component
@RequiredArgsConstructor
public class DeviceAddedEventHandler extends BaseHubEventHandler<DeviceAddedEventAvro> {
    /**
     * Хранилище датчиков.
     */
    private final SensorRepository sensorRepository;

    @Override
    protected void handle(String hubId, DeviceAddedEventAvro hubEvent) {
        if (sensorRepository.existsByIdAndHubId(hubEvent.getId(), hubId)) {
            return;
        }

        Sensor sensor = Sensor.builder()
                .id(hubEvent.getId())
                .hubId(hubId)
                .build();

        sensorRepository.save(sensor);
    }
}
