package ru.yandex.practicum.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.warehouse.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.dto.AddressDto;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.service.ProductService;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Контроллер для работы со складом.
 */
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
@RestController
@Slf4j
public class WarehouseController {
    /**
     * Сервис для работы с товарами на складе.
     */
    private final ProductService productService;

    /**
     * Добавить на склад новый товар.
     *
     * @param newProductInWarehouseRequest запрос на добавление нового товара на склад.
     */
    @PutMapping
    public void createProduct(@RequestBody @Valid NewProductInWarehouseRequest newProductInWarehouseRequest) {
        log.info("Add new product to warehouse - {}", newProductInWarehouseRequest);
        productService.createProduct(newProductInWarehouseRequest);
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody @Valid AddProductToWarehouseRequest addProductToWarehouseRequest) {
        log.info("Add quantity of product to warehouse - {}", addProductToWarehouseRequest);
        productService.addProduct(addProductToWarehouseRequest);
    }

    // TODO: blank

    private static final String[] ADDRESSES = new String[]{"ADDRESS_1", "ADDRESS_2"};

    private static final String CURRENT_ADDRESS = ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

    /**
     * Предоставить адрес склада для расчёта доставки.
     */
    @GetMapping("/address")
    public AddressDto getWarehouseAddress() {
        return AddressDto.builder()
                .country(CURRENT_ADDRESS)
                .city(CURRENT_ADDRESS)
                .street(CURRENT_ADDRESS)
                .house(CURRENT_ADDRESS)
                .flat(CURRENT_ADDRESS)
                .build();
    }
}
