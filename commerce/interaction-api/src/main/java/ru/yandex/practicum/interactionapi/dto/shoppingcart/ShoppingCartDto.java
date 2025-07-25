package ru.yandex.practicum.interactionapi.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

/**
 * Корзина товаров в онлайн магазине.
 */
@Builder(toBuilder = true)
@Data
public class ShoppingCartDto {
    /**
     * Идентификатор корзины в БД.
     */
    @NotNull
    private UUID shoppingCartId;

    /**
     * Отображение идентификатора товара на отобранное количество.
     */
    @NotNull
    private Map<UUID, Integer> products;
}
