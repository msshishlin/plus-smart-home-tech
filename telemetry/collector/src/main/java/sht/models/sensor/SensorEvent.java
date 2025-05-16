package sht.models.sensor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 * Событие датчика.
 */
@Getter
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClimateSensorEvent.class, name = "CLIMATE_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = LightSensorEvent.class, name = "LIGHT_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = MotionSensorEvent.class, name = "MOTION_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = SwitchSensorEvent.class, name = "SWITCH_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = TemperatureSensorEvent.class, name = "TEMPERATURE_SENSOR_EVENT")
})
@JsonTypeInfo(
        defaultImpl = SensorEventType.class,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        use = JsonTypeInfo.Id.NAME
)
@Setter
@ToString
public abstract class SensorEvent {
    /**
     * Идентификатор события датчика.
     */
    @NotEmpty(message = "Идентификатор события датчика не может быть пустым")
    private String id;

    /**
     * Идентификатор хаба, связанного с событием.
     */
    @NotEmpty(message = "Идентификатор хаба, связанного с событием не может быть пустым")
    private String hubId;

    /**
     * Временная метка события.
     * <p>
     * По умолчанию устанавливается текущее время.
     */
    private Instant timestamp = Instant.now();

    /**
     * Получить тип события датчика.
     *
     * @return тип события датчика.
     */
    public abstract SensorEventType getType();
}
