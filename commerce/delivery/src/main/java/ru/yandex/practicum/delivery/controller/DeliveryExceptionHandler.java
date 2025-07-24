package ru.yandex.practicum.delivery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.interactionapi.exception.delivery.NoDeliveryFoundException;

/**
 * Обработчик исключений, возникающих в сервисе.
 */
@RestControllerAdvice
public class DeliveryExceptionHandler {
    /**
     * Обработать исключение, выбрасываемое сервисом, если доставка не была найдена.
     *
     * @param noDeliveryFoundException исключение, выбрасываемое сервисом, если доставка не была найдена.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleNoDeliveryFoundException(final NoDeliveryFoundException noDeliveryFoundException) {
        return new ResponseEntity<>(noDeliveryFoundException, HttpStatus.NOT_FOUND);
    }
}
