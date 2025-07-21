package ru.yandex.practicum.warehouse.service;

import ru.yandex.practicum.interactionapi.dto.shoppingcart.CartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.NewProductInWarehouseRequest;

/**
 * Контракт сервиса для работы с товарами на складе.
 */
public interface ProductService {
    /**
     * Добавить на склад новый товар.
     *
     * @param newProductInWarehouseRequest запрос на добавление нового товара на склад.
     */
    void createProduct(NewProductInWarehouseRequest newProductInWarehouseRequest);

    /**
     * Добавить на склад определенное количество товара.
     *
     * @param addProductToWarehouseRequest запрос на увеличение единиц товара по его идентификатору.
     */
    void addProduct(AddProductToWarehouseRequest addProductToWarehouseRequest);

    /**
     * Предварительно проверить что количество товаров на складе достаточно для данной корзины продуктов.
     *
     * @param cartDto корзина товаров.
     */
    BookedProductsDto checkProductQuantity(CartDto cartDto);
}
