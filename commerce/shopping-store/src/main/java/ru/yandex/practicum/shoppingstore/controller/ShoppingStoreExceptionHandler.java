package ru.yandex.practicum.shoppingstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.interactionapi.exception.shoppingstore.ProductNotFoundException;

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
    public ResponseEntity<Exception> handleProductNotFoundException(final ProductNotFoundException productNotFoundException) {
        return new ResponseEntity<>(productNotFoundException, HttpStatus.NOT_FOUND);
    }
}
