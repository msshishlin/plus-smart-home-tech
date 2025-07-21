package ru.yandex.practicum.warehouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.warehouse.exception.ProductInShoppingCartLowQuantityInWarehouseException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleProductInShoppingCartLowQuantityInWarehouseException(final ProductInShoppingCartLowQuantityInWarehouseException productInShoppingCartLowQuantityInWarehouseException) {
        Map<String, Object> errorAttributes = new HashMap<>();

        errorAttributes.put("cause", productInShoppingCartLowQuantityInWarehouseException.getCause());
        errorAttributes.put("stackTrace", productInShoppingCartLowQuantityInWarehouseException.getStackTrace());

        return errorAttributes;
    }
}
