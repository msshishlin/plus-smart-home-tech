package ru.yandex.practicum.warehouse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.CartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.dto.warehouse.AddressDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.interactionapi.feign.WarehouseClient;
import ru.yandex.practicum.warehouse.service.ProductService;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Контроллер для работы со складом.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class WarehouseController implements WarehouseClient {
    /**
     * Сервис для работы с товарами на складе.
     */
    private final ProductService productService;

    @Override
    public void createProduct(NewProductInWarehouseRequest newProductInWarehouseRequest) {
        log.info("Add new product to warehouse - {}", newProductInWarehouseRequest);
        productService.createProduct(newProductInWarehouseRequest);
    }

    @Override
    public void addProduct(AddProductToWarehouseRequest addProductToWarehouseRequest) {
        log.info("Add quantity of product to warehouse - {}", addProductToWarehouseRequest);
        productService.addProduct(addProductToWarehouseRequest);
    }

    @Override
    public BookedProductsDto checkProductQuantity(CartDto cartDto) {
        log.info("Checking quantity of products - {}", cartDto);
        return productService.checkProductQuantity(cartDto);
    }

    // TODO: blank

    private static final String[] ADDRESSES = new String[]{"ADDRESS_1", "ADDRESS_2"};

    private static final String CURRENT_ADDRESS = ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

    @Override
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
