package ru.yandex.practicum.shoppingstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.interactionapi.exception.shoppingstore.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Обработчик исключений, возникающих в сервисе.
 */
@RestControllerAdvice
public class ShoppingStoreExceptionHandler {
    /**
     * Обработать исключение, выбрасываемое сервисом, если товар не был найден.
     *
     * @param productNotFoundException исключение, выбрасываемое сервисом, если товар не был найден.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleProductNotFoundException(final ProductNotFoundException productNotFoundException) {
        Map<String, Object> errorAttributes = new HashMap<>();

        errorAttributes.put("cause", productNotFoundException.getCause());
        errorAttributes.put("stackTrace", productNotFoundException.getStackTrace());

        return errorAttributes;
    }
}
