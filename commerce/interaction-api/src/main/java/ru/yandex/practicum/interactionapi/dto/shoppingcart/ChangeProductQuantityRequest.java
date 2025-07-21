package ru.yandex.practicum.interactionapi.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

/**
 * Запрос на изменение количества товара в корзине.
 */
@Data
public class ChangeProductQuantityRequest {
    /**
     * Идентификатор товара.
     */
    @NotNull
    private UUID productId;

    /**
     * Новое количество товара.
     */
    @NotNull
    @Positive
    private Integer newQuantity;
}
