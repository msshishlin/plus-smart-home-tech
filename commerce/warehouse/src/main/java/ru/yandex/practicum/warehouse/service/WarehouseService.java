package ru.yandex.practicum.warehouse.service;

import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.*;
import ru.yandex.practicum.interactionapi.exception.warehouse.*;

import java.util.Map;
import java.util.UUID;

/**
 * Контракт сервиса для работы с товарами на складе.
 */
public interface WarehouseService {
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
     * @throws ProductInShoppingCartNotInWarehouse товар из корзины отсутствует на складе.
     */
    BookedProductsDto checkProductQuantity(ShoppingCartDto shoppingCartDto) throws ProductInShoppingCartNotInWarehouse;

    /**
     * Собрать товары к заказу для подготовки к отправке.
     *
     * @param assemblyProductsForOrderRequest запрос на сбор заказа из товаров.
     * @return общие сведения по бронированию.
     * @throws ProductInShoppingCartLowQuantityInWarehouseException товар из корзины не находится в требуемом количестве на складе
     */
    BookedProductsDto assemblyProductsForOrder(AssemblyProductsForOrderRequest assemblyProductsForOrderRequest) throws ProductInShoppingCartLowQuantityInWarehouseException;

    /**
     * Передать товары в доставку.
     *
     * @param shippedToDeliveryRequest Запрос на передачу в доставку товаров.
     * @throws NoOrderBookingFoundException не найдены забронированные товары для заказа.
     */
    void shippedToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest) throws NoOrderBookingFoundException;

    /**
     * Принять возврат товаров на склад.
     *
     * @param products отображение идентификатора товара на отобранное количество.
     */
    void acceptReturnOfProducts(Map<UUID, Integer> products);
}
