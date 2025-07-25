package ru.yandex.practicum.delivery.service;

import ru.yandex.practicum.interactionapi.dto.delivery.DeliveryDto;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.interactionapi.exception.delivery.NoDeliveryFoundException;

import java.util.UUID;

/**
 * Контракт сервиса для работы с доставками.
 */
public interface DeliveryService {
    /**
     * Создать новую доставку в БД.
     *
     * @param deliveryDto доставка.
     * @return доставка.
     */
    DeliveryDto createDelivery(DeliveryDto deliveryDto);

    /**
     * Расчёт полной стоимости доставки заказа.
     *
     * @param orderDto заказ для расчёта.
     * @return полная стоимость доставки заказа.
     * @throws NoDeliveryFoundException не найдена доставка для расчёта.
     */
    double calculateDeliveryCost(OrderDto orderDto) throws NoDeliveryFoundException;

    /**
     * Эмуляция получения товара в доставку.
     *
     * @param deliveryId идентификатор доставки.
     * @throws NoDeliveryFoundException не найдена доставка для выдачи.
     */
    void productsPickedToDelivery(UUID deliveryId) throws NoDeliveryFoundException;

    /**
     * Эмуляция успешной доставки товара.
     *
     * @param deliveryId идентификатор доставки.
     * @throws NoDeliveryFoundException не найдена доставка.
     */
    void handleSuccessfulDelivery(UUID deliveryId) throws NoDeliveryFoundException;

    /**
     * Эмуляция неудачного вручения товара.
     *
     * @param deliveryId идентификатор доставки.
     * @throws NoDeliveryFoundException не найдена доставка для сбоя.
     */
    void handleFailureDelivery(UUID deliveryId) throws NoDeliveryFoundException;
}
