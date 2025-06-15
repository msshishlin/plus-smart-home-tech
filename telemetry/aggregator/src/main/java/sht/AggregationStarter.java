package sht;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import sht.kafka.AggregatorKafkaClient;
import sht.kafka.AggregatorKafkaTopics;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AggregationStarter {
    /**
     * Клиент Kafka.
     */
    private final AggregatorKafkaClient kafkaClient;

    /**
     * Словарь снэпшотов, в котором в качестве ключа выступает идентификатор хаба.
     */
    private final Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

    /**
     * Метод для начала процесса агрегации данных.
     * Подписывается на топики для получения событий от датчиков,
     * формирует снимок их состояния и записывает в кафку.
     */
    public void start() {
        Consumer<String, SpecificRecordBase> consumer = kafkaClient.getConsumer();
        Producer<String, SpecificRecordBase> producer = kafkaClient.getProducer();

        try {
            consumer.subscribe(List.of(AggregatorKafkaTopics.TELEMETRY_SENSORS_V1));

            while (true) {
                ConsumerRecords<String, SpecificRecordBase> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, SpecificRecordBase> record : records) {
                    if (record.value() instanceof SensorEventAvro) {
                        updateState((SensorEventAvro) record.value())
                                .ifPresent(sensorsSnapshotAvro -> producer.send(new ProducerRecord<>(AggregatorKafkaTopics.TELEMETRY_SNAPSHOTS_V1, sensorsSnapshotAvro)));
                    }
                }

                consumer.commitSync();
            }

        } catch (WakeupException ignored) {
            // игнорируем - закрываем консьюмер и продюсер в блоке finally
        } catch (Exception e) {
            log.error("Ошибка во время обработки событий от датчиков", e);
        } finally {
            kafkaClient.stop();
        }
    }

    private Optional<SensorsSnapshotAvro> updateState(SensorEventAvro event) {
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
