package ru.yandex.practicum.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.interactionapi.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.interactionapi.dto.order.OrderState;
import ru.yandex.practicum.interactionapi.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.interactionapi.exception.order.NoOrderFoundException;
import ru.yandex.practicum.interactionapi.exception.warehouse.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.interactionapi.feign.DeliveryClient;
import ru.yandex.practicum.interactionapi.feign.PaymentClient;
import ru.yandex.practicum.interactionapi.feign.WarehouseClient;
import ru.yandex.practicum.order.model.Order;
import ru.yandex.practicum.order.repository.OrderRepository;
import ru.yandex.practicum.order.service.mapper.OrderMapper;

import java.util.UUID;

/**
 * Сервис для работы с заказами.
 */
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    /**
     * Хранилище данных для заказов.
     */
    private final OrderRepository orderRepository;

    /**
     * Маппер для сущности заказа.
     */
    private final OrderMapper orderMapper;

    /**
     * Клиент для сервиса доставки заказов.
     */
    private final DeliveryClient deliveryClient;

    /**
     * Клиент для платежного сервиса.
     */
    private final PaymentClient paymentClient;

    /**
     * Клиент для сервиса склада товаров.
     */
    private final WarehouseClient warehouseClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto createOrder(CreateNewOrderRequest request) throws NoSpecifiedProductInWarehouseException {
        BookedProductsDto bookedProductsDto = warehouseClient.checkProductQuantity(request.getShoppingCart());

        Order order = Order.builder()
                .shoppingCartId(request.getShoppingCart().getShoppingCartId())
                .products(request.getShoppingCart().getProducts())
                .paymentId(UUID.randomUUID())
                .deliveryId(UUID.randomUUID())
                .state(OrderState.NEW)
                .deliveryWeight(bookedProductsDto.getDeliveryWeight())
                .deliveryVolume(bookedProductsDto.getDeliveryVolume())
                .fragile(bookedProductsDto.isFragile())
                .productPrice(paymentClient.calculateProductCost(OrderDto.builder().products(request.getShoppingCart().getProducts()).build()))
                .build();

        return orderMapper.mapToOrderDto(orderRepository.save(order));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto calculateDeliveryCost(UUID orderId) throws NoOrderFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setDeliveryPrice(deliveryClient.calculateDeliveryCost(orderMapper.mapToOrderDto(order)));

        return orderMapper.mapToOrderDto(orderRepository.save(order));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto calculateTotalCost(UUID orderId) throws NoOrderFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setTotalPrice(paymentClient.calculateTotalCost(orderMapper.mapToOrderDto(order)));

        return orderMapper.mapToOrderDto(orderRepository.save(order));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleSuccessfulPayment(UUID orderId) throws NoOrderFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.PAID);

        return orderMapper.mapToOrderDto(orderRepository.save(order));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleFailurePayment(UUID orderId) throws NoOrderFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.PAYMENT_FAILED);

        return orderMapper.mapToOrderDto(orderRepository.save(order));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleSuccessfulOrderAssembly(UUID orderId) throws NoOrderFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.ASSEMBLED);

        return orderMapper.mapToOrderDto(orderRepository.save(order));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleFailureOrderAssembly(UUID orderId) throws NoOrderFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.ASSEMBLY_FAILED);

        return orderMapper.mapToOrderDto(orderRepository.save(order));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleSuccessfulDelivery(UUID orderId) throws NoOrderFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.DELIVERED);

        return orderMapper.mapToOrderDto(orderRepository.save(order));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleFailureDelivery(UUID orderId) throws NoOrderFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.DELIVERY_FAILED);

        return orderMapper.mapToOrderDto(orderRepository.save(order));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDto handleOrderCompletion(UUID orderId) throws NoOrderFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.DONE);

        return orderMapper.mapToOrderDto(orderRepository.save(order));
    }
}
