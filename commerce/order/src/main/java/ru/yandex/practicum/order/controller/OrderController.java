package ru.yandex.practicum.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.interactionapi.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.interactionapi.exception.order.NoOrderFoundException;
import ru.yandex.practicum.interactionapi.exception.warehouse.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.interactionapi.feign.OrderClient;
import ru.yandex.practicum.order.service.OrderService;

import java.util.UUID;

/**
 * Контроллер для работы с заказами покупателя.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController implements OrderClient {
    /**
     * Сервис для работы с заказами.
     */
    private final OrderService orderService;

    /**
     * {@inheritDoc}
     */
    public OrderDto createOrder(CreateNewOrderRequest request) throws NoSpecifiedProductInWarehouseException {
        return orderService.createOrder(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto calculateDeliveryCost(UUID orderId) throws NoOrderFoundException {
        return orderService.calculateDeliveryCost(orderId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto calculateTotalCost(UUID orderId) throws NoOrderFoundException {
        return orderService.calculateTotalCost(orderId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleSuccessfulPayment(UUID orderId) throws NoOrderFoundException {
        return orderService.handleSuccessfulPayment(orderId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleFailurePayment(UUID orderId) throws NoOrderFoundException {
        return orderService.handleFailurePayment(orderId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleSuccessfulOrderAssembly(UUID orderId) throws NoOrderFoundException {
        return orderService.handleSuccessfulOrderAssembly(orderId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleFailureOrderAssembly(UUID orderId) throws NoOrderFoundException {
        return orderService.handleFailureOrderAssembly(orderId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleSuccessfulDelivery(UUID orderId) throws NoOrderFoundException {
        return orderService.handleSuccessfulDelivery(orderId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleFailureDelivery(UUID orderId) throws NoOrderFoundException {
        return orderService.handleFailureDelivery(orderId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleOrderCompletion(UUID orderId) throws NoOrderFoundException {
        return orderService.handleOrderCompletion(orderId);
    }
}
