package ru.yandex.practicum.aggregator.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.aggregator.kafka.consumer.KafkaSnapshotConsumer;
import ru.yandex.practicum.aggregator.service.handler.SensorsSnapshotHandler;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

/**
 * Сервис для обработки снапшотов состояний датчиков.
 */
@Component
@Slf4j
public class SensorsSnapshotProcessor {
    /**
     * Потребитель данных Kafka.
     */
    private final KafkaSnapshotConsumer consumer;

    /**
     * Обработчик снапшота состояний датчиков.
     */
    private final SensorsSnapshotHandler sensorsSnapshotHandler;

    /**
     * Конструктор.
     *
     * @param consumer               потребитель данных Kafka.
     * @param sensorsSnapshotHandler обработчик снапшота состояний датчиков.
     */
    public SensorsSnapshotProcessor(KafkaSnapshotConsumer consumer, SensorsSnapshotHandler sensorsSnapshotHandler) {
        this.consumer = consumer;
        this.sensorsSnapshotHandler = sensorsSnapshotHandler;
    }

    /**
     * Запуск сервиса.
     */
    public void start() {
        try {
            consumer.subscribe();

            while (true) {
                ConsumerRecords<String, SpecificRecordBase> records = consumer.poll();

                for (ConsumerRecord<String, SpecificRecordBase> record : records) {
                    if (record.value() instanceof SensorsSnapshotAvro sensorsSnapshot) {
                        sensorsSnapshotHandler.handle(sensorsSnapshot);
                    }
                }
            }
        } catch (WakeupException ignored) {
            // игнорируем - закрываем консьюмер и продюсер в блоке finally
        } catch (Exception e) {
            log.error("Ошибка во время обработки событий от датчиков", e);
        } finally {
            consumer.stop();
        }
    }
}
