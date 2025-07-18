package ru.yandex.practicum.shoppingcart.exception;

public class NoProductsInCartException extends RuntimeException {
    public NoProductsInCartException(String message) {
        super(message);
    }
}
