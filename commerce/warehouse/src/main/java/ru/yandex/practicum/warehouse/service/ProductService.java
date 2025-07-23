package ru.yandex.practicum.warehouse.service;

import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.interactionapi.exception.warehouse.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.interactionapi.exception.warehouse.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.interactionapi.exception.warehouse.SpecifiedProductAlreadyInWarehouseException;

/**
 * Контракт сервиса для работы с товарами на складе.
 */
public interface ProductService {
    /**
     * Добавить на склад новый товар.
     *
     * @param newProductInWarehouseRequest запрос на добавление нового товара на склад.
     * @throws SpecifiedProductAlreadyInWarehouseException товар с такими параметрами уже зарегистрирован на складе.
     */
    void createNewProductInWarehouse(NewProductInWarehouseRequest newProductInWarehouseRequest) throws SpecifiedProductAlreadyInWarehouseException;

    /**
     * Добавить на склад определенное количество товара.
     *
     * @param addProductToWarehouseRequest запрос на увеличение единиц товара по его идентификатору.
     * @throws NoSpecifiedProductInWarehouseException нет информации о товаре на складе.
     */
    void addProductToWarehouse(AddProductToWarehouseRequest addProductToWarehouseRequest) throws NoSpecifiedProductInWarehouseException;

    /**
     * Предварительно проверить что количество товаров на складе достаточно для данной корзины продуктов.
     *
     * @param shoppingCartDto корзина товаров.
     * @throws ProductInShoppingCartLowQuantityInWarehouseException товар из корзины не находится в требуемом количестве на складе.
     */
    BookedProductsDto checkProductQuantity(ShoppingCartDto shoppingCartDto) throws ProductInShoppingCartLowQuantityInWarehouseException;
}
