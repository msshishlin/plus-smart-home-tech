package ru.yandex.practicum.interactionapi.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.interactionapi.dto.AddressDto;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;

/**
 * Запрос на создание нового заказа.
 */
@Data
public class CreateNewOrderRequest {
    /**
     * Корзина товаров.
     */
    @NotNull
    private ShoppingCartDto shoppingCart;

    /**
     * Адрес доставки.
     */
    @NotNull
    private AddressDto deliveryAddress;
}
