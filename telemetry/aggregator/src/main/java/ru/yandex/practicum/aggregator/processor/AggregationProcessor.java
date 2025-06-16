package ru.yandex.practicum.aggregator.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.aggregator.kafka.consumer.KafkaSensorConsumer;
import ru.yandex.practicum.aggregator.kafka.producer.KafkaSnapshotProducer;
import ru.yandex.practicum.aggregator.storage.SnapshotStorage;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Component
@RequiredArgsConstructor
@Slf4j
public class AggregationProcessor {
    private final KafkaSensorConsumer consumer;
    private final KafkaSnapshotProducer producer;

    private final SnapshotStorage storage;

    /**
     * Метод для начала процесса агрегации данных.
     */
    public void start() {
        try {
            consumer.subscribe();

            while (true) {
                ConsumerRecords<String, SpecificRecordBase> records = consumer.poll();

                for (ConsumerRecord<String, SpecificRecordBase> record : records) {
                    if (record.value() instanceof SensorEventAvro) {
                        storage.updateState((SensorEventAvro) record.value()).ifPresent(producer::sendSensorSnapshot);
                    }
                }
            }
        } catch (WakeupException ignored) {
            // игнорируем - закрываем консьюмер и продюсер в блоке finally
        } catch (Exception e) {
            log.error("Ошибка во время обработки событий от датчиков", e);
        } finally {
            consumer.stop();
            producer.stop();
        }
    }
}
