package ru.yandex.practicum.interactionapi.exception.warehouse;

/**
 * Исключение, выбрасываемое сервисом, если на складе не зарегистрирован запрошенный товар.
 */
public class NoSpecifiedProductInWarehouseException extends RuntimeException {
    /**
     * Конструктор.
     */
    public NoSpecifiedProductInWarehouseException() {
        super("Нет информации о товаре на складе");
    }
}
