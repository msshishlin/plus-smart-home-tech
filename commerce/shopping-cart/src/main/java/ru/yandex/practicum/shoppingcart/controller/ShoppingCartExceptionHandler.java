package ru.yandex.practicum.shoppingcart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.interactionapi.exception.shoppingcart.NoProductsInShoppingCartException;

/**
 * Обработчик исключений, возникающих в сервисе.
 */
@RestControllerAdvice
public class ShoppingCartExceptionHandler {
    /**
     * Обработать исключение, выбрасываемое сервисом, если в корзине пользователя нет товаров, над которыми необходимо провести действия.
     *
     * @param noProductsInShoppingCartException исключение, выбрасываемое сервисом, если в корзине пользователя нет товаров, над которыми необходимо провести действия.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleNoProductsInShoppingCartException(final NoProductsInShoppingCartException noProductsInShoppingCartException) {
        return new ResponseEntity<>(noProductsInShoppingCartException, HttpStatus.BAD_REQUEST);
    }
}
