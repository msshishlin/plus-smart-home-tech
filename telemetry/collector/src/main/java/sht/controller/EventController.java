package sht.controller;

import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sht.kafka.CollectorKafkaClient;
import sht.kafka.CollectorKafkaTopics;
import sht.mapper.HubEventMapper;
import sht.mapper.SensorEventMapper;
import sht.models.hub.HubEvent;
import sht.models.sensor.SensorEvent;

@RequestMapping("/events")
@RequiredArgsConstructor
@RestController
public class EventController {
    /**
     * Клиент Kafka.
     */
    private final CollectorKafkaClient kafkaClient;

    /**
     * Эндпоинт для обработки событий от хаба.
     *
     * @param event данные события хаба (регистрация/удаление устройств в хабе, добавление/удаление сценария умного дома).
     */
    @PostMapping("/hubs")
    public void collectHubEvent(@RequestBody @Valid HubEvent event) {
        kafkaClient.getProducer().send(new ProducerRecord<>(CollectorKafkaTopics.TELEMETRY_HUBS_V1, HubEventMapper.INSTANCE.toHubEventAvro(event)));
    }

    /**
     * Эндпоинт для обработки событий от датчиков.
     *
     * @param event данные события датчика (показания, изменение состояния и т.д).
     */
    @PostMapping("/sensors")
    public void collectSensorEvent(@RequestBody @Valid SensorEvent event) {
        kafkaClient.getProducer().send(new ProducerRecord<>(CollectorKafkaTopics.TELEMETRY_SENSORS_V1, SensorEventMapper.INSTANCE.toSensorEventAvro(event)));
    }

    @PreDestroy
    public void preDestroy() {
        kafkaClient.stop();
    }
}
