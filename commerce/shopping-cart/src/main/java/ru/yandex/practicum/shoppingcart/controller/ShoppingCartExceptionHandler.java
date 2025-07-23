package ru.yandex.practicum.shoppingcart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.interactionapi.exception.shoppingcart.NoProductsInShoppingCartException;

import java.util.HashMap;
import java.util.Map;

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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleNoProductsInShoppingCartException(final NoProductsInShoppingCartException noProductsInShoppingCartException) {
        Map<String, Object> errorAttributes = new HashMap<>();

        errorAttributes.put("cause", noProductsInShoppingCartException.getCause());
        errorAttributes.put("stackTrace", noProductsInShoppingCartException.getStackTrace());

        return errorAttributes;
    }
}
