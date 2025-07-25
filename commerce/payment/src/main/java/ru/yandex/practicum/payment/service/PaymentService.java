package ru.yandex.practicum.payment.service;

import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.interactionapi.dto.payment.PaymentDto;
import ru.yandex.practicum.interactionapi.exception.order.NoOrderFoundException;
import ru.yandex.practicum.interactionapi.exception.payment.NoPaymentFoundException;
import ru.yandex.practicum.interactionapi.exception.payment.NotEnoughInfoInOrderToCalculateException;

import java.util.UUID;

/**
 * Контракт сервиса для работы с оплатами заказов.
 */
public interface PaymentService {
    /**
     * Расчёт стоимости товаров в заказе.
     *
     * @param orderDto представление заказа в системе.
     * @return стоимость товаров в заказе.
     * @throws NotEnoughInfoInOrderToCalculateException недостаточно информации в заказе для расчёта.
     */
    double calculateProductCost(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException;

    /**
     * Расчёт полной стоимости заказа.
     *
     * @param orderDto представление заказа в системе.
     * @return полная стоимость заказа.
     * @throws NotEnoughInfoInOrderToCalculateException недостаточно информации в заказе для расчёта.
     */
    double calculateTotalCost(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException;

    /**
     * Формирование оплаты для заказа (переход в платежный шлюз).
     *
     * @return информация об оплате.
     * @throws NotEnoughInfoInOrderToCalculateException недостаточно информации в заказе для расчёта.
     */
    PaymentDto createPayment(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException;

    /**
     * Метод для эмуляции успешной оплаты.
     *
     * @param paymentId идентификатор платежа.
     * @throws NoOrderFoundException   заказ не найден.
     * @throws NoPaymentFoundException платёж не найден.
     */
    void handleSuccessfulPayment(UUID paymentId) throws NoOrderFoundException, NoPaymentFoundException;

    /**
     * Метод для эмуляции отказа в оплате.
     *
     * @param paymentId идентификатор платежа.
     * @throws NoOrderFoundException   заказ не найден.
     * @throws NoPaymentFoundException платёж не найден.
     */
    void handleFailurePayment(UUID paymentId) throws NoOrderFoundException, NoPaymentFoundException;
}
