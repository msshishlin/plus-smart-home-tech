package sht.models.hub;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 * Событие, происходящее в хабе.
 */
@Getter
@JsonSubTypes({
        @JsonSubTypes.Type(value = DeviceAddedEvent.class, name = "DEVICE_ADDED"),
        @JsonSubTypes.Type(value = DeviceRemovedEvent.class, name = "DEVICE_REMOVED"),
        @JsonSubTypes.Type(value = ScenarioAddedEvent.class, name = "SCENARIO_ADDED"),
        @JsonSubTypes.Type(value = ScenarioRemovedEvent.class, name = "SCENARIO_REMOVED"),
})
@JsonTypeInfo(
        defaultImpl = HubEventType.class,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        use = JsonTypeInfo.Id.NAME
)
@Setter
@ToString
public abstract class HubEvent {
    /**
     * Идентификатор хаба, связанного с событием.
     */
    @NotEmpty(message = "Идентификатор хаба, связанного с событием, не может быть пустым")
    private String hubId;

    /**
     * Временная метка события.
     * <p>
     * По умолчанию устанавливается текущее время.
     */
    private final Instant timestamp = Instant.now();

    /**
     * Получить тип события, происходящего в хабе.
     *
     * @return тип события, происходящего в хабе.
     */
    public abstract HubEventType getType();
}
