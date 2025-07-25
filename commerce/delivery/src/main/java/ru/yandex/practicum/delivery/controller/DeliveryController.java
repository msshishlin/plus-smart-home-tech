package ru.yandex.practicum.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.delivery.service.DeliveryService;
import ru.yandex.practicum.interactionapi.dto.delivery.DeliveryDto;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.interactionapi.exception.delivery.NoDeliveryFoundException;
import ru.yandex.practicum.interactionapi.feign.DeliveryClient;

import java.util.UUID;

/**
 * Контроллер для работы с доставками.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class DeliveryController implements DeliveryClient {
    /**
     * Сервис для работы с доставками.
     */
    private final DeliveryService deliveryService;

    /**
     * {@inheritDoc}
     */
    @Override
    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        log.info("Creating delivery - {}", deliveryDto);
        return deliveryService.createDelivery(deliveryDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateDeliveryCost(OrderDto orderDto) throws NoDeliveryFoundException {
        log.info("Calculation delivery cost for order - {}", orderDto);
        return deliveryService.calculateDeliveryCost(orderDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void productsPickedToDelivery(UUID deliveryId) throws NoDeliveryFoundException {
        log.info("Mark products as picked for delivery with id={}", deliveryId);
        deliveryService.productsPickedToDelivery(deliveryId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulDelivery(UUID deliveryId) throws NoDeliveryFoundException {
        log.info("Handle successful delivery with id={}", deliveryId);
        deliveryService.handleSuccessfulDelivery(deliveryId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailureDelivery(UUID deliveryId) throws NoDeliveryFoundException {
        log.info("Handle failure delivery with id={}", deliveryId);
        deliveryService.handleFailureDelivery(deliveryId);
    }
}
