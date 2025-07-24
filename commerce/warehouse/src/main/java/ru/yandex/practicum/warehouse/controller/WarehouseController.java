package ru.yandex.practicum.warehouse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.interactionapi.dto.AddressDto;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.*;
import ru.yandex.practicum.interactionapi.exception.warehouse.NoOrderBookingFoundException;
import ru.yandex.practicum.interactionapi.exception.warehouse.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.interactionapi.exception.warehouse.ProductInShoppingCartNotInWarehouse;
import ru.yandex.practicum.interactionapi.exception.warehouse.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.interactionapi.feign.WarehouseClient;
import ru.yandex.practicum.warehouse.service.WarehouseService;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Контроллер для работы с товарами на складе.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class WarehouseController implements WarehouseClient {
    /**
     * Список адресов склада.
     */
    private static final String[] ADDRESSES = new String[]{"ADDRESS_1", "ADDRESS_2"};

    /**
     * Текущий адрес склада.
     */
    private static final String CURRENT_ADDRESS = ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

    /**
     * Сервис для работы со товарами на складе.
     */
    private final WarehouseService warehouseService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createNewProductInWarehouse(NewProductInWarehouseRequest newProductInWarehouseRequest) throws SpecifiedProductAlreadyInWarehouseException {
        log.info("Adding new product to warehouse - {}", newProductInWarehouseRequest);
        warehouseService.createNewProductInWarehouse(newProductInWarehouseRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest addProductToWarehouseRequest) throws NoSpecifiedProductInWarehouseException {
        log.info("Adding quantity of product to warehouse - {}", addProductToWarehouseRequest);
        warehouseService.addProductToWarehouse(addProductToWarehouseRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookedProductsDto checkProductQuantity(ShoppingCartDto shoppingCartDto) throws ProductInShoppingCartNotInWarehouse {
        log.info("Checking quantity of products - {}", shoppingCartDto);
        return warehouseService.checkProductQuantity(shoppingCartDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookedProductsDto assemblyProductsForOrder(AssemblyProductsForOrderRequest assemblyProductsForOrderRequest) throws ProductInShoppingCartNotInWarehouse {
        log.info("Assembly products for order - {}", assemblyProductsForOrderRequest);
        return warehouseService.assemblyProductsForOrder(assemblyProductsForOrderRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shippedToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest) throws NoOrderBookingFoundException {
        log.info("Pass products to delivery - {}", shippedToDeliveryRequest);
        warehouseService.shippedToDelivery(shippedToDeliveryRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void acceptReturnOfProducts(Map<UUID, Integer> products) {
        log.info("Accept return of products - {}", products);
        warehouseService.acceptReturnOfProducts(products);
    }

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
