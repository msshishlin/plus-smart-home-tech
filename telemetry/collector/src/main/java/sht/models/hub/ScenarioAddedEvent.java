package sht.models.hub;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.Collection;

/**
 * Событие добавления сценария в систему. Содержит информацию о названии сценария, условиях и действиях.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ScenarioAddedEvent extends HubEvent {
    /**
     * Название добавленного сценария.
     */
    @Length(min = 3, message = "Название добавленного сценария не может быть меньше 3-х символов")
    @NotEmpty(message = "Название добавленного сценария не может быть пустым")
    private String name;

    /**
     * Список условий, которые связаны со сценарием.
     */
    @NotEmpty(message = "Список условий не может быть пустым")
    private Collection<ScenarioCondition> conditions;

    /**
     * Список действий, которые должны быть выполнены в рамках сценария.
     */
    @NotEmpty(message = "Список действий не может быть пустым")
    private Collection<DeviceAction> actions;

    /**
     * Получить тип события, происходящего в хабе.
     *
     * @return тип события, происходящего в хабе.
     */
    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
