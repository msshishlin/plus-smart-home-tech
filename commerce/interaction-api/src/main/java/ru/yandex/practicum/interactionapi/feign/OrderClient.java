package ru.yandex.practicum.interactionapi.feign;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.interactionapi.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.interactionapi.exception.order.NoOrderFoundException;
import ru.yandex.practicum.interactionapi.exception.warehouse.NoSpecifiedProductInWarehouseException;

import java.util.UUID;

/**
 * Контракт клиента для сервиса заказов.
 */
@FeignClient(name = "order")
public interface OrderClient {
    /**
     * Создание нового заказа.
     *
     * @param request запрос на создание нового заказа.
     * @return оформленный заказ пользователя.
     * @throws NoSpecifiedProductInWarehouseException нет заказываемого товара на складе.
     */
    @PutMapping("/api/v1/order")
    OrderDto createOrder(@RequestBody @Valid CreateNewOrderRequest request) throws NoSpecifiedProductInWarehouseException;

    /**
     * Расчёт стоимости доставки заказа.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя с расчётом доставки.
     * @throws NoOrderFoundException заказ не найден.
     */
    @PostMapping("/api/v1/order/calculate/delivery")
    OrderDto calculateDeliveryCost(@NotNull @RequestBody UUID orderId) throws NoOrderFoundException;

    /**
     * Расчёт стоимости заказа.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя с расчётом общей стоимости.
     * @throws NoOrderFoundException заказ не найден.
     */
    @PostMapping("/api/v1/order/calculate/total")
    OrderDto calculateTotalCost(@NotNull @RequestBody UUID orderId) throws NoOrderFoundException;

    /**
     * Оплата заказа.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после оплаты.
     * @throws NoOrderFoundException заказ не найден.
     */
    @PostMapping("/api/v1/order/payment")
    OrderDto handleSuccessfulPayment(@NotNull @RequestBody UUID orderId) throws NoOrderFoundException;

    /**
     * Оплата заказа произошла с ошибкой.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после ошибки оплаты.
     * @throws NoOrderFoundException заказ не найден.
     */
    @PostMapping("/api/v1/order/payment/failed")
    OrderDto handleFailurePayment(@NotNull @RequestBody UUID orderId) throws NoOrderFoundException;

    /**
     * Сборка заказа.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после ошибки оплаты.
     * @throws NoOrderFoundException заказ не найден.
     */
    @PostMapping("/api/v1/order/assembly")
    OrderDto handleSuccessfulOrderAssembly(@NotNull @RequestBody UUID orderId) throws NoOrderFoundException;

    /**
     * Сборка заказа произошла с ошибкой.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после ошибки оплаты.
     * @throws NoOrderFoundException заказ не найден.
     */
    @PostMapping("/api/v1/order/assembly/failed")
    OrderDto handleFailureOrderAssembly(@NotNull @RequestBody UUID orderId) throws NoOrderFoundException;

    /**
     * Доставка заказа.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после доставки.
     * @throws NoOrderFoundException заказ не найден.
     */
    @PostMapping("/api/v1/order/delivery")
    OrderDto handleSuccessfulDelivery(@NotNull @RequestBody UUID orderId) throws NoOrderFoundException;

    /**
     * Доставка заказа произошла с ошибкой.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после ошибки доставки.
     * @throws NoOrderFoundException заказ не найден.
     */
    @PostMapping("/api/v1/order/delivery/failed")
    OrderDto handleFailureDelivery(@NotNull @RequestBody UUID orderId) throws NoOrderFoundException;

    /**
     * Завершение заказа.
     *
     * @param orderId идентификатор заказа.
     * @return заказ пользователя после всех стадий и завершенный.
     * @throws NoOrderFoundException заказ не найден.
     */
    @PostMapping("/api/v1/order/completed")
    OrderDto handleOrderCompletion(@NotNull @RequestBody UUID orderId) throws NoOrderFoundException;
}
