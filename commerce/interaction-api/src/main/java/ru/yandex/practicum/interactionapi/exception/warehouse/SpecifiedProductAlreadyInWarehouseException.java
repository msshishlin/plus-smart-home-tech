package ru.yandex.practicum.interactionapi.exception.warehouse;

/**
 * Исключение, выбрасываемое сервисом, если на складе уже существует товар с такими параметрами.
 */
public class SpecifiedProductAlreadyInWarehouseException extends RuntimeException {
    /**
     * Конструктор.
     */
    public SpecifiedProductAlreadyInWarehouseException() {
        super("Товар с такими параметрами уже зарегистрирован на складе");
    }
}
