package ru.yandex.practicum.interactionapi.exception.shoppingstore;

import java.util.UUID;

/**
 * Исключение, выбрасываемое сервисом, если товар не был найден.
 */
public class ProductNotFoundException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param productId идентификатор товара.
     */
    public ProductNotFoundException(UUID productId) {
        super(String.format("Товар с id=%s не найден", productId));
    }
}
