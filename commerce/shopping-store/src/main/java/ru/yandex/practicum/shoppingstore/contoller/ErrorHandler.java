package ru.yandex.practicum.shoppingstore.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.shoppingstore.exception.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleProductNotFoundException(final ProductNotFoundException productNotFoundException) {
        Map<String, Object> errorAttributes = new HashMap<>();

        errorAttributes.put("cause", productNotFoundException.getCause());
        errorAttributes.put("stackTrace", productNotFoundException.getStackTrace());

        return errorAttributes;
    }
}
