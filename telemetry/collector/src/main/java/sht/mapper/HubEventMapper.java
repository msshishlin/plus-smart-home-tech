package sht.mapper;

import org.apache.avro.specific.SpecificRecordBase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.kafka.telemetry.event.*;
import sht.models.hub.*;

@Mapper
public interface HubEventMapper {
    /**
     * Экземпляр маппера для моделей, содержащих информацию о событии, происходящем в хабе.
     */
    HubEventMapper INSTANCE = Mappers.getMapper(HubEventMapper.class);

    @Mapping(target = "payload", source = ".", qualifiedByName = "getPayload")
    HubEventAvro toHubEventAvro(HubEvent event);

    @Mapping(source = "deviceType", target = "type")
    DeviceAddedEventAvro toDeviceAddedEventAvro(DeviceAddedEvent event);

    DeviceRemovedEventAvro toDeviceRemovedEventAvro(DeviceRemovedEvent event);

    ScenarioAddedEventAvro toScenarioAddedEventAvro(ScenarioAddedEvent event);

    ScenarioRemovedEventAvro toScenarioRemovedEventAvro(ScenarioRemovedEvent event);

    @Named("getPayload")
    static Object getPayload(HubEvent event) {
        if (event instanceof DeviceAddedEvent deviceAddedEvent) {
            return HubEventMapper.INSTANCE.toDeviceAddedEventAvro(deviceAddedEvent);
        }

        if (event instanceof DeviceRemovedEvent deviceRemovedEvent) {
            return HubEventMapper.INSTANCE.toDeviceRemovedEventAvro(deviceRemovedEvent);
        }

        if (event instanceof ScenarioAddedEvent scenarioAddedEvent) {
            return HubEventMapper.INSTANCE.toScenarioAddedEventAvro(scenarioAddedEvent);
        }

        if (event instanceof ScenarioRemovedEvent scenarioRemovedEvent) {
            return HubEventMapper.INSTANCE.toScenarioRemovedEventAvro(scenarioRemovedEvent);
        }

        throw new RuntimeException("Не удалось выполнить преобразование типа " + event.getClass().getName() + " в тип " + SpecificRecordBase.class.getName());
    }
}
