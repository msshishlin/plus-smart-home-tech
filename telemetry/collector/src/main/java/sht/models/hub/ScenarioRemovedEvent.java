package sht.models.hub;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * Событие удаления сценария из системы. Содержит информацию о названии удаленного сценария.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ScenarioRemovedEvent extends HubEvent {
    /**
     * Название удаленного сценария.
     */
    @Length(min = 3, message = "Название удаленного сценария не может быть меньше 3-х символов")
    @NotEmpty(message = "Название удаленного сценария не может быть пустым")
    private String name;

    /**
     * Получить тип события, происходящего в хабе.
     *
     * @return тип события, происходящего в хабе.
     */
    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_REMOVED;
    }
}
