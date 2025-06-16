package ru.yandex.practicum.aggregator.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Хранилище снапшотов.
 */
@Component
public class SnapshotStorage {
    /**
     * Словарь снэпшотов, в котором в качестве ключа выступает идентификатор хаба.
     */
    private final Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

    public Optional<SensorsSnapshotAvro> updateState(SensorEventAvro event) {
        SensorsSnapshotAvro snapshot;

        if (snapshots.containsKey(event.getHubId())) {
            snapshot = snapshots.get(event.getHubId());
        } else {
            snapshot = SensorsSnapshotAvro.newBuilder()
                    .setHubId(event.getHubId())
                    .setSensorsState(new HashMap<>())
                    .setTimestamp(event.getTimestamp())
                    .build();

            snapshots.put(event.getHubId(), snapshot);
        }

        if (snapshot.getSensorsState().containsKey(event.getId())) {
            SensorStateAvro oldSensorState = snapshot.getSensorsState().get(event.getId());
            if (oldSensorState.getTimestamp().isAfter(event.getTimestamp()) || oldSensorState.getData().equals(event.getPayload())) {
                return Optional.empty();
            }
        }

        SensorStateAvro sensorState = SensorStateAvro.newBuilder()
                .setData(event.getPayload())
                .setTimestamp(event.getTimestamp())
                .build();

        snapshot.getSensorsState().put(event.getId(), sensorState);
        snapshot.setTimestamp(event.getTimestamp());

        return Optional.of(snapshot);
    }
}
