package ru.yandex.practicum.shoppingcart.dto;

import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    private UUID productId;

    /**
     * Новое количество товара.
     */
    @NotNull
    @Positive
    private Integer newQuantity;
}
