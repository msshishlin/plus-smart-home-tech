package ru.yandex.practicum.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.interactionapi.exception.order.NoOrderFoundException;
import ru.yandex.practicum.interactionapi.exception.payment.NoPaymentFoundException;
import ru.yandex.practicum.interactionapi.exception.payment.NotEnoughInfoInOrderToCalculateException;

/**
 * Обработчик исключений, возникающих в сервисе.
 */
@RestControllerAdvice
public class PaymentExceptionHandler {
    /**
     * Обработать исключение, выбрасываемое сервисом, если в заказе недостаточно информации для расчета стоимости.
     *
     * @param notEnoughInfoInOrderToCalculateException исключение, выбрасываемое сервисом, если в заказе недостаточно информации для расчета стоимости.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleNotEnoughInfoInOrderToCalculateException(final NotEnoughInfoInOrderToCalculateException notEnoughInfoInOrderToCalculateException) {
        return new ResponseEntity<>(notEnoughInfoInOrderToCalculateException, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработать исключение, выбрасываемое сервисом, если в заказе недостаточно информации для расчета стоимости.
     *
     * @param noOrderFoundException исключение, выбрасываемое сервисом, если в заказе недостаточно информации для расчета стоимости.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleNoOrderFoundException(final NoOrderFoundException noOrderFoundException) {
        return new ResponseEntity<>(noOrderFoundException, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработать исключение, выбрасываемое сервисом, если платёж не был найден.
     *
     * @param noPaymentFoundException исключение, выбрасываемое сервисом, если платёж не был найден.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleNoPaymentFoundException(final NoPaymentFoundException noPaymentFoundException) {
        return new ResponseEntity<>(noPaymentFoundException, HttpStatus.NOT_FOUND);
    }
}
