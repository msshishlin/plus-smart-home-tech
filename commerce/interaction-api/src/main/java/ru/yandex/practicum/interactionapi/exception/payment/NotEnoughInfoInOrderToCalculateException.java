package ru.yandex.practicum.interactionapi.exception.payment;

/**
 * Исключение, выбрасываемое сервисом, если в заказе недостаточно информации для расчета стоимости.
 */
public class NotEnoughInfoInOrderToCalculateException extends RuntimeException {
    /**
     * Конструктор.
     */
    public NotEnoughInfoInOrderToCalculateException() {
        super("Недостаточно информации в заказе для расчёта");
    }
}
