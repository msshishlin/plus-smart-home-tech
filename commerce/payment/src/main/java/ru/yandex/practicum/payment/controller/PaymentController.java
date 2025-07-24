package ru.yandex.practicum.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.interactionapi.dto.payment.PaymentDto;
import ru.yandex.practicum.interactionapi.exception.order.NoOrderFoundException;
import ru.yandex.practicum.interactionapi.exception.payment.NoPaymentFoundException;
import ru.yandex.practicum.interactionapi.exception.payment.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.interactionapi.feign.PaymentClient;
import ru.yandex.practicum.payment.service.PaymentService;

import java.util.UUID;

/**
 * Контроллер для работы с оплатами заказов.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class PaymentController implements PaymentClient {
    /**
     * Сервис для работы с оплатами заказов.
     */
    private final PaymentService paymentService;

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateProductCost(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException {
        log.info("Calculating product cost for order - {}", orderDto);
        return paymentService.calculateProductCost(orderDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateTotalCost(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException {
        log.info("Calculation total cost for order - {}", orderDto);
        return paymentService.calculateTotalCost(orderDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaymentDto createPayment(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException {
        log.info("Creating payment for order - {}", orderDto);
        return paymentService.createPayment(orderDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulPayment(UUID paymentId) throws NoOrderFoundException, NoPaymentFoundException {
        log.info("Handle successful payment with id={}", paymentId);
        paymentService.handleSuccessfulPayment(paymentId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailurePayment(UUID paymentId) throws NoOrderFoundException, NoPaymentFoundException {
        log.info("Handle failure payment with id={}", paymentId);
        paymentService.handleFailurePayment(paymentId);
    }
}
