package ru.yandex.practicum.payment.model;

/**
 * Состояние платежа.
 */
public enum PaymentState {
    /**
     * Ожидает оплаты.
     */
    PENDING,

    /**
     * Успешно оплачен.
     */
    SUCCESS,

    /**
     * Ошибка в процессе оплаты.
     */
    FAILED
}
