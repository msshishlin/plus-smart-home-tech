package ru.yandex.practicum.collector.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.yandex.practicum.collector.service.handler.HubEventHandler;
import ru.yandex.practicum.collector.service.handler.SensorEventHandler;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@GrpcService
public class EventController extends CollectorControllerGrpc.CollectorControllerImplBase {
    /**
     * Словарь обработчиков данных событий в хабе.
     */
    private final Map<HubEventProto.PayloadCase, HubEventHandler> hubEventHandlers;

    /**
     * Словарь обработчиков данных событий датчиков.
     */
    private final Map<SensorEventProto.PayloadCase, SensorEventHandler> sensorEventHandlers;

    /**
     * Конструктор.
     *
     * @param hubEventHandlers    коллекция обработчиков данных событий в хабе.
     * @param sensorEventHandlers коллекция обработчиков данных событий датчиков.
     */
    public EventController(List<HubEventHandler> hubEventHandlers, List<SensorEventHandler> sensorEventHandlers) {
        this.hubEventHandlers = hubEventHandlers.stream().collect(Collectors.toMap(HubEventHandler::getHubEventType, Function.identity()));
        this.sensorEventHandlers = sensorEventHandlers.stream().collect(Collectors.toMap(SensorEventHandler::getSensorEventType, Function.identity()));
    }

    /**
     * Обработка событий от хаба.
     *
     * @param request данные события хаба (регистрация/удаление устройств в хабе, добавление/удаление сценария умного дома).
     */
    @Override
    public void collectHubEvent(HubEventProto request, StreamObserver<Empty> responseObserver) {
        try {
            HubEventHandler hubEventHandler = hubEventHandlers.get(request.getPayloadCase());
            if (hubEventHandler == null) {
                throw new IllegalArgumentException("Не могу найти обработчик для события " + request.getPayloadCase());

            }
            hubEventHandler.handle(request);

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
            SensorEventHandler sensorEventHandler = sensorEventHandlers.get(request.getPayloadCase());
            if (sensorEventHandler == null) {
                throw new IllegalArgumentException("Не могу найти обработчик для события " + request.getPayloadCase());
            }
            sensorEventHandler.handle(request);

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
}
