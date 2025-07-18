package ru.yandex.practicum.shoppingcart.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

/**
 * Корзина товаров в онлайн магазине.
 */
@Builder(toBuilder = true)
@Data
public class CartDto {
    /**
     * Идентификатор корзины в БД.
     */
    private UUID shoppingCartId;

    /**
     * Отображение идентификатора товара на отобранное количество.
     */
    private Map<UUID, Integer> products;
}
