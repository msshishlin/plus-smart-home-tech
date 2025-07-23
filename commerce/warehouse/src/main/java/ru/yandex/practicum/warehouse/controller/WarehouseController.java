package ru.yandex.practicum.warehouse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.interactionapi.dto.AddressDto;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.interactionapi.exception.warehouse.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.interactionapi.exception.warehouse.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.interactionapi.exception.warehouse.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.interactionapi.feign.WarehouseClient;
import ru.yandex.practicum.warehouse.service.ProductService;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Контроллер для работы с товарами на складе.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class WarehouseController implements WarehouseClient {
    /**
     * Сервис для работы со товарами на складе.
     */
    private final ProductService productService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createNewProductInWarehouse(NewProductInWarehouseRequest newProductInWarehouseRequest) throws SpecifiedProductAlreadyInWarehouseException {
        log.info("Adding new product to warehouse - {}", newProductInWarehouseRequest);
        productService.createNewProductInWarehouse(newProductInWarehouseRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest addProductToWarehouseRequest) throws NoSpecifiedProductInWarehouseException {
        log.info("Adding quantity of product to warehouse - {}", addProductToWarehouseRequest);
        productService.addProductToWarehouse(addProductToWarehouseRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookedProductsDto checkProductQuantity(ShoppingCartDto shoppingCartDto) throws ProductInShoppingCartLowQuantityInWarehouseException {
        log.info("Checking quantity of products - {}", shoppingCartDto);
        return productService.checkProductQuantity(shoppingCartDto);
    }

    // TODO: blank

    private static final String[] ADDRESSES = new String[]{"ADDRESS_1", "ADDRESS_2"};

    private static final String CURRENT_ADDRESS = ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

    /**
     * {@inheritDoc}
     */
    @Override
    public AddressDto getWarehouseAddress() {
        log.info("Getting warehouse address");
        return AddressDto.builder()
                .country(CURRENT_ADDRESS)
                .city(CURRENT_ADDRESS)
                .street(CURRENT_ADDRESS)
                .house(CURRENT_ADDRESS)
                .flat(CURRENT_ADDRESS)
                .build();
    }
}
