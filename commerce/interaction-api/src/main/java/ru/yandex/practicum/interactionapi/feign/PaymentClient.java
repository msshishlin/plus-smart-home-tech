package ru.yandex.practicum.interactionapi.feign;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.interactionapi.dto.payment.PaymentDto;
import ru.yandex.practicum.interactionapi.exception.order.NoOrderFoundException;
import ru.yandex.practicum.interactionapi.exception.payment.NoPaymentFoundException;
import ru.yandex.practicum.interactionapi.exception.payment.NotEnoughInfoInOrderToCalculateException;

import java.util.UUID;

/**
 * Контракт клиента для сервиса оплаты заказов.
 */
@FeignClient(name = "payment")
public interface PaymentClient {
    /**
     * Расчёт стоимости товаров в заказе.
     *
     * @param orderDto представление заказа в системе.
     * @return стоимость товаров в заказе.
     * @throws NotEnoughInfoInOrderToCalculateException недостаточно информации в заказе для расчёта.
     */
    @PostMapping("/api/v1/payment/productCost")
    double calculateProductCost(@RequestBody @Valid OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException;

    /**
     * Расчёт полной стоимости заказа.
     *
     * @param orderDto представление заказа в системе.
     * @return полная стоимость заказа.
     * @throws NotEnoughInfoInOrderToCalculateException недостаточно информации в заказе для расчёта.
     */
    @PostMapping("/api/v1/payment/totalCost")
    double calculateTotalCost(@RequestBody @Valid OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException;

    /**
     * Формирование оплаты для заказа (переход в платежный шлюз).
     *
     * @return информация об оплате.
     * @throws NotEnoughInfoInOrderToCalculateException недостаточно информации в заказе для расчёта.
     */
    @PostMapping("/api/v1/payment")
    PaymentDto createPayment(@RequestBody @Valid OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException;

    /**
     * Метод для эмуляции успешной оплаты.
     *
     * @param paymentId идентификатор платежа.
     * @throws NoOrderFoundException   заказ не найден.
     * @throws NoPaymentFoundException платёж не найден.
     */
    @PostMapping("/api/v1/payment/refund")
    void handleSuccessfulPayment(@NotNull @RequestBody UUID paymentId) throws NoOrderFoundException, NoPaymentFoundException;

    /**
     * Метод для эмуляции отказа в оплате.
     *
     * @param paymentId идентификатор платежа.
     * @throws NoOrderFoundException   заказ не найден.
     * @throws NoPaymentFoundException платёж не найден.
     */
    @PostMapping("/api/v1/payment/failed")
    void handleFailurePayment(@NotNull @RequestBody UUID paymentId) throws NoOrderFoundException, NoPaymentFoundException;
}
