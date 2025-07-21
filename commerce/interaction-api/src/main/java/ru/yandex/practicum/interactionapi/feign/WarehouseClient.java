package ru.yandex.practicum.interactionapi.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.CartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.dto.warehouse.AddressDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.NewProductInWarehouseRequest;

@FeignClient(name = "warehouse")
public interface WarehouseClient {
    /**
     * Добавить на склад новый товар.
     *
     * @param newProductInWarehouseRequest запрос на добавление нового товара на склад.
     */
    @PutMapping("/api/v1/warehouse")
    void createProduct(@RequestBody @Valid NewProductInWarehouseRequest newProductInWarehouseRequest);

    /**
     * Добавить на склад определенное количество товара.
     *
     * @param addProductToWarehouseRequest запрос на увеличение единиц товара по его идентификатору.
     */
    @PostMapping("/api/v1/warehouse/add")
    void addProduct(@RequestBody @Valid AddProductToWarehouseRequest addProductToWarehouseRequest);

    /**
     * Предварительно проверить что количество товаров на складе достаточно для данной корзины продуктов.
     *
     * @param cartDto корзина товаров.
     */
    @PostMapping("/api/v1/warehouse/check")
    BookedProductsDto checkProductQuantity(@RequestBody @Valid CartDto cartDto);

    /**
     * Предоставить адрес склада для расчёта доставки.
     */
    @GetMapping("/api/v1/warehouse/address")
    AddressDto getWarehouseAddress();
}
