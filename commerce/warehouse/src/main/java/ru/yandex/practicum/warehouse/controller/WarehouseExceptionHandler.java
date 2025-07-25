package ru.yandex.practicum.warehouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.interactionapi.exception.warehouse.*;

/**
 * Обработчик исключений, возникающих в сервисе.
 */
@RestControllerAdvice
public class WarehouseExceptionHandler {
    /**
     * Обработать исключение, выбрасываемое сервисом, если для заказа не были найдены забронированные товары.
     *
     * @param noOrderBookingFoundException исключение, выбрасываемое сервисом, если для заказа не были найдены забронированные товары.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleNoOrderBookingFoundException(final NoOrderBookingFoundException noOrderBookingFoundException) {
        return new ResponseEntity<>(noOrderBookingFoundException, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработать исключение, выбрасываемое сервисом, если на складе не зарегистрирован запрошенный товар.
     *
     * @param noSpecifiedProductInWarehouseException исключение, выбрасываемое сервисом, если на складе не зарегистрирован запрошенный товар.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleNoSpecifiedProductInWarehouseException(final NoSpecifiedProductInWarehouseException noSpecifiedProductInWarehouseException) {
        return new ResponseEntity<>(noSpecifiedProductInWarehouseException, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработать исключение, выбрасываемое сервисом, если товара на складе меньше, чем в корзине товаров пользователя.
     *
     * @param productInShoppingCartLowQuantityInWarehouseException исключение, выбрасываемое сервисом, если товара на складе меньше, чем в корзине товаров пользователя.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleProductInShoppingCartLowQuantityInWarehouseException(final ProductInShoppingCartLowQuantityInWarehouseException productInShoppingCartLowQuantityInWarehouseException) {
        return new ResponseEntity<>(productInShoppingCartLowQuantityInWarehouseException, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработать исключение, выбрасываемое сервисом, если товары, указанные в корзине отсутствуют на складе.
     *
     * @param productInShoppingCartNotInWarehouse исключение, выбрасываемое сервисом, если товары, указанные в корзине отсутствуют на складе.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleProductInShoppingCartLowQuantityInWarehouseException(final ProductInShoppingCartNotInWarehouse productInShoppingCartNotInWarehouse) {
        return new ResponseEntity<>(productInShoppingCartNotInWarehouse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработать исключение, выбрасываемое сервисом, если на складе уже существует товар с такими параметрами.
     *
     * @param specifiedProductAlreadyInWarehouseException исключение, выбрасываемое сервисом, если на складе уже существует товар с такими параметрами.
     * @return результат обработки исключения.
     */
    @ExceptionHandler
    public ResponseEntity<Exception> handleSpecifiedProductAlreadyInWarehouseException(final SpecifiedProductAlreadyInWarehouseException specifiedProductAlreadyInWarehouseException) {
        return new ResponseEntity<>(specifiedProductAlreadyInWarehouseException, HttpStatus.BAD_REQUEST);
    }
}
