package ru.yandex.practicum.interactionapi.feign;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.interactionapi.dto.delivery.DeliveryDto;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.interactionapi.exception.delivery.NoDeliveryFoundException;

import java.util.UUID;

/**
 * Контракт клиента для сервиса доставки заказов.
 */
@FeignClient(name = "delivery")
public interface DeliveryClient {
    /**
     * Создать новую доставку в БД.
     *
     * @param dto доставка.
     * @return доставка.
     */
    @PutMapping("/api/v1/delivery")
    DeliveryDto createDelivery(@RequestBody @Valid DeliveryDto dto);

    /**
     * Расчёт полной стоимости доставки заказа.
     *
     * @param orderDto заказ для расчёта.
     * @return полная стоимость доставки заказа.
     * @throws NoDeliveryFoundException не найдена доставка для расчёта.
     */
    @PostMapping("/api/v1/delivery/cost")
    double calculateDeliveryCost(@RequestBody @Valid OrderDto orderDto) throws NoDeliveryFoundException;

    /**
     * Эмуляция получения товара в доставку.
     *
     * @param deliveryId идентификатор доставки.
     * @throws NoDeliveryFoundException не найдена доставка для выдачи.
     */
    @PostMapping("/api/v1/delivery/picked")
    void productsPickedToDelivery(@NotNull @RequestBody UUID deliveryId) throws NoDeliveryFoundException;

    /**
     * Эмуляция успешной доставки товара.
     *
     * @param deliveryId идентификатор доставки.
     * @throws NoDeliveryFoundException не найдена доставка.
     */
    @PostMapping("/api/v1/delivery/successful")
    void handleSuccessfulDelivery(@NotNull @RequestBody UUID deliveryId) throws NoDeliveryFoundException;

    /**
     * Эмуляция неудачного вручения товара.
     *
     * @param deliveryId идентификатор доставки.
     * @throws NoDeliveryFoundException не найдена доставка для сбоя.
     */
    @PostMapping("/api/v1/delivery/failed")
    void handleFailureDelivery(@NotNull @RequestBody UUID deliveryId) throws NoDeliveryFoundException;
}
