package sht.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import sht.kafka.CollectorKafkaClient;
import sht.kafka.CollectorKafkaTopics;
import sht.mapper.HubEventMapper;
import sht.mapper.SensorEventMapper;

@GrpcService
@RequiredArgsConstructor
public class EventController extends CollectorControllerGrpc.CollectorControllerImplBase {
    /**
     * Клиент Kafka.
     */
    private final CollectorKafkaClient kafkaClient;

    /**
     * Обработка событий от хаба.
     *
     * @param request данные события хаба (регистрация/удаление устройств в хабе, добавление/удаление сценария умного дома).
     */
    @Override
    public void collectHubEvent(HubEventProto request, StreamObserver<Empty> responseObserver) {
        try {
            kafkaClient.getProducer().send(new ProducerRecord<>(CollectorKafkaTopics.TELEMETRY_HUBS_V1, HubEventMapper.INSTANCE.toHubEventAvro(request)));

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception exception) {
            responseObserver.onError(
                    new StatusRuntimeException(
                            Status.INTERNAL.withDescription(exception.getLocalizedMessage())
                                    .withCause(exception)
                    )
            );
        }

    }

    /**
     * Обработка событий от датчиков.
     *
     * @param request данные события датчика (показания, изменение состояния и т.д).
     */
    @Override
    public void collectSensorEvent(SensorEventProto request, StreamObserver<Empty> responseObserver) {
        try {
            kafkaClient.getProducer().send(new ProducerRecord<>(CollectorKafkaTopics.TELEMETRY_SENSORS_V1, SensorEventMapper.INSTANCE.toSensorEventAvro(request)));

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception exception) {
            responseObserver.onError(
                    new StatusRuntimeException(
                            Status.INTERNAL.withDescription(exception.getLocalizedMessage())
                                    .withCause(exception)
                    )
            );
        }
    }

    @PreDestroy
    public void preDestroy() {
        kafkaClient.stop();
    }
}
