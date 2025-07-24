package ru.yandex.practicum.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.interactionapi.dto.payment.PaymentDto;
import ru.yandex.practicum.interactionapi.exception.order.NoOrderFoundException;
import ru.yandex.practicum.interactionapi.exception.payment.NoPaymentFoundException;
import ru.yandex.practicum.interactionapi.exception.payment.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.interactionapi.exception.shoppingstore.ProductNotFoundException;
import ru.yandex.practicum.interactionapi.feign.OrderClient;
import ru.yandex.practicum.interactionapi.feign.ShoppingStoreClient;
import ru.yandex.practicum.payment.configuration.PaymentConfiguration;
import ru.yandex.practicum.payment.model.Payment;
import ru.yandex.practicum.payment.model.PaymentState;
import ru.yandex.practicum.payment.repository.PaymentRepository;
import ru.yandex.practicum.payment.service.mapper.PaymentMapper;

import java.util.Map;
import java.util.UUID;

/**
 * Сервис для работы с оплатами заказов.
 */
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    /**
     * Конфигурация сервиса оплаты заказов.
     */
    private final PaymentConfiguration paymentConfiguration;

    /**
     * Хранилище данных об оплатах.
     */
    private final PaymentRepository paymentRepository;

    /**
     * Маппер для сущности оплаты.
     */
    private final PaymentMapper paymentMapper;

    /**
     * Клиент для сервиса заказов.
     */
    private final OrderClient orderClient;

    /**
     * Клиент для сервиса витрины товаров.
     */
    private final ShoppingStoreClient shoppingStoreClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateProductCost(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException {
        float productCost = 0;

        for (Map.Entry<UUID, Integer> product : orderDto.getProducts().entrySet()) {
            try {
                productCost = productCost + shoppingStoreClient.findById(product.getKey()).getPrice() * product.getValue();
            } catch (ProductNotFoundException ex) {
                throw new NotEnoughInfoInOrderToCalculateException();
            }
        }

        return productCost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateTotalCost(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException {
        if (orderDto.getProductPrice() == null || orderDto.getDeliveryPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException();
        }

        return orderDto.getProductPrice() + orderDto.getProductPrice() * paymentConfiguration.getValueAddedTax() / 100 + orderDto.getDeliveryPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaymentDto createPayment(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException {
        return paymentMapper.mapToPaymentDto(paymentRepository.save(Payment.builder()
                .totalPayment(orderDto.getTotalPrice())
                .feeTotal(orderDto.getProductPrice() * paymentConfiguration.getValueAddedTax() / 100)
                .deliveryTotal(orderDto.getDeliveryPrice())
                .state(PaymentState.PENDING)
                .build()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulPayment(UUID paymentId) throws NoOrderFoundException, NoPaymentFoundException {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new NoPaymentFoundException(paymentId));
        payment.setState(PaymentState.SUCCESS);

        orderClient.handleSuccessfulPayment(payment.getOrderId());
        paymentRepository.save(payment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailurePayment(UUID paymentId) throws NoOrderFoundException, NoPaymentFoundException {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new NoPaymentFoundException(paymentId));
        payment.setState(PaymentState.FAILED);

        orderClient.handleFailurePayment(payment.getOrderId());
        paymentRepository.save(payment);
    }
}
