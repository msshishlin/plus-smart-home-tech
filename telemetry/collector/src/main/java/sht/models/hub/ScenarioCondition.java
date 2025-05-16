package sht.models.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Условие сценария, которое содержит информацию о датчике, типе условия, операции и значении.
 */
@Getter
@Setter
@ToString
public final class ScenarioCondition {
    /**
     * Идентификатор датчика, связанного с условием.
     */
    private String sensorId;

    /**
     * Тип условия.
     */
    private ScenarioConditionType type;

    /**
     * Операция, которая может быть использована в условии.
     */
    private ScenarioConditionOperation operation;

    /**
     * Значение, используемое в условии.
     */
    private int value;
}
