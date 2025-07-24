package ru.yandex.practicum.interactionapi.exception.payment;

import java.util.UUID;

/**
 * Исключение, выбрасываемое сервисом, если платёж не был найден.
 */
public class NoPaymentFoundException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param paymentId идентификатор платежа.
     */
    public NoPaymentFoundException(UUID paymentId) {
        super(String.format("Платёж с id=%s не найден", paymentId));
    }
}
