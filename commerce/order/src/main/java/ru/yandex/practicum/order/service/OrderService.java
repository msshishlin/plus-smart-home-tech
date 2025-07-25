package ru.yandex.practicum.order.service;

import ru.yandex.practicum.interactionapi.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.interactionapi.exception.order.NoOrderFoundException;
import ru.yandex.practicum.interactionapi.exception.warehouse.NoSpecifiedProductInWarehouseException;

import java.util.UUID;

/**
 * Контракт сервиса для работы с заказами.
 */
public interface OrderService {
    /**
     * Создание нового заказа.
     *
     * @param request запрос на создание нового заказа.
     * @return заказ.
     * @throws NoSpecifiedProductInWarehouseException нет заказываемого товара на складе.
     */
    OrderDto createOrder(CreateNewOrderRequest request) throws NoSpecifiedProductInWarehouseException;

    /**
     * Расчёт стоимости доставки заказа.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя с расчётом доставки.
     * @throws NoOrderFoundException заказ не найден.
     */
    OrderDto calculateDeliveryCost(UUID orderId) throws NoOrderFoundException;

    /**
     * Расчёт стоимости заказа.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя с расчётом общей стоимости.
     * @throws NoOrderFoundException заказ не найден.
     */
    OrderDto calculateTotalCost(UUID orderId) throws NoOrderFoundException;

    /**
     * Оплата заказа.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после оплаты.
     * @throws NoOrderFoundException заказ не найден.
     */
    OrderDto handleSuccessfulPayment(UUID orderId) throws NoOrderFoundException;

    /**
     * Оплата заказа произошла с ошибкой.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после ошибки оплаты.
     * @throws NoOrderFoundException заказ не найден.
     */
    OrderDto handleFailurePayment(UUID orderId) throws NoOrderFoundException;

    /**
     * Сборка заказа.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после ошибки оплаты.
     * @throws NoOrderFoundException заказ не найден.
     */
    OrderDto handleSuccessfulOrderAssembly(UUID orderId) throws NoOrderFoundException;

    /**
     * Сборка заказа произошла с ошибкой.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после ошибки оплаты.
     * @throws NoOrderFoundException заказ не найден.
     */
    OrderDto handleFailureOrderAssembly(UUID orderId) throws NoOrderFoundException;

    /**
     * Доставка заказа.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после доставки.
     * @throws NoOrderFoundException заказ не найден.
     */
    OrderDto handleSuccessfulDelivery(UUID orderId) throws NoOrderFoundException;

    /**
     * Доставка заказа произошла с ошибкой.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после ошибки доставки.
     * @throws NoOrderFoundException заказ не найден.
     */
    OrderDto handleFailureDelivery(UUID orderId) throws NoOrderFoundException;

    /**
     * Завершение заказа.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после всех стадий и завершенный.
     * @throws NoOrderFoundException заказ не найден.
     */
    OrderDto handleOrderCompletion(UUID orderId) throws NoOrderFoundException;
}
