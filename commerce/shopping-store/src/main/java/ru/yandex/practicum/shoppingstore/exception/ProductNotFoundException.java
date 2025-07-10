package ru.yandex.practicum.shoppingstore.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(UUID productId) {
        super(String.format("Товар с id=%s не найден", productId));
    }
}
