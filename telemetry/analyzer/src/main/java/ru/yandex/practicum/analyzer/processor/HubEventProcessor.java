package ru.yandex.practicum.analyzer.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.kafka.consumer.KafkaHubEventConsumer;
import ru.yandex.practicum.analyzer.service.handler.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Сервис для обработки данных об изменениях в хабах.
 */
@Component
@Slf4j
public class HubEventProcessor implements Runnable {
    /**
     * Потребитель данных Kafka.
     */
    private final KafkaHubEventConsumer consumer;

    /**
     * Словарь обработчиков данных событий в хабе.
     */
    private final Map<Class<?>, HubEventHandler> hubEventHandlers;

    public HubEventProcessor(KafkaHubEventConsumer consumer, List<HubEventHandler> hubEventHandlers) {
        this.consumer = consumer;
        this.hubEventHandlers = hubEventHandlers.stream().collect(Collectors.toMap(HubEventHandler::getHubEventType, Function.identity()));
    }

    /**
     * Запуск сервиса.
     */
    @Override
    public void run() {
        try {
            consumer.subscribe();

            while (true) {
                ConsumerRecords<String, SpecificRecordBase> records = consumer.poll();

                for (ConsumerRecord<String, SpecificRecordBase> record : records) {
                    if (record.value() instanceof HubEventAvro hubEvent) {
                        HubEventHandler hubEventHandler = hubEventHandlers.get(hubEvent.getPayload().getClass());
                        if (hubEventHandler != null) {
                            hubEventHandler.handle(hubEvent);
                        }
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
