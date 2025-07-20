package ru.yandex.practicum.warehouse.service;

import ru.yandex.practicum.warehouse.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseRequest;

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
}
