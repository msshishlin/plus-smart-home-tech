package ru.yandex.practicum.interactionapi.feign;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.interactionapi.dto.AddressDto;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.*;
import ru.yandex.practicum.interactionapi.exception.warehouse.NoOrderBookingFoundException;
import ru.yandex.practicum.interactionapi.exception.warehouse.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.interactionapi.exception.warehouse.ProductInShoppingCartNotInWarehouse;
import ru.yandex.practicum.interactionapi.exception.warehouse.SpecifiedProductAlreadyInWarehouseException;

import java.util.Map;
import java.util.UUID;

/**
 * Контракт клиента для сервиса склада товаров.
 */
@FeignClient(name = "warehouse")
public interface WarehouseClient {
    /**
     * Добавить на склад новый товар.
     *
     * @param newProductInWarehouseRequest запрос на добавление нового товара на склад.
     * @throws SpecifiedProductAlreadyInWarehouseException товар с такими параметрами уже зарегистрирован на складе.
     */
    @PutMapping("/api/v1/warehouse")
    void createNewProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest newProductInWarehouseRequest) throws SpecifiedProductAlreadyInWarehouseException;

    /**
     * Добавить на склад определенное количество товара.
     *
     * @param addProductToWarehouseRequest запрос на увеличение единиц товара по его идентификатору.
     * @throws NoSpecifiedProductInWarehouseException нет информации о товаре на складе.
     */
    @PostMapping("/api/v1/warehouse/add")
    void addProductToWarehouse(@RequestBody @Valid AddProductToWarehouseRequest addProductToWarehouseRequest) throws NoSpecifiedProductInWarehouseException;

    /**
     * Предварительно проверить что количество товаров на складе достаточно для данной корзины продуктов.
     *
     * @param shoppingCartDto корзина товаров.
     * @throws ProductInShoppingCartNotInWarehouse товар из корзины отсутствует на складе.
     */
    @PostMapping("/api/v1/warehouse/check")
    BookedProductsDto checkProductQuantity(@RequestBody @Valid ShoppingCartDto shoppingCartDto) throws ProductInShoppingCartNotInWarehouse;

    /**
     * Собрать товары к заказу для подготовки к отправке.
     *
     * @param assemblyProductsForOrderRequest запрос на сбор заказа из товаров.
     * @return общие сведения по бронированию.
     * @throws ProductInShoppingCartNotInWarehouse товар из корзины отсутствует на складе.
     */
    @PostMapping("/api/v1/warehouse/assembly")
    BookedProductsDto assemblyProductsForOrder(@RequestBody @Valid AssemblyProductsForOrderRequest assemblyProductsForOrderRequest) throws ProductInShoppingCartNotInWarehouse;

    /**
     * Передать товары в доставку.
     *
     * @param shippedToDeliveryRequest Запрос на передачу в доставку товаров.
     * @throws NoOrderBookingFoundException не найдены забронированные товары для заказа.
     */
    @PostMapping("/api/v1/warehouse/shipped")
    void shippedToDelivery(@RequestBody @Valid ShippedToDeliveryRequest shippedToDeliveryRequest) throws NoOrderBookingFoundException;

    /**
     * Принять возврат товаров на склад.
     *
     * @param products отображение идентификатора товара на отобранное количество.
     */
    @PostMapping("/api/v1/warehouse/return")
    void acceptReturnOfProducts(@NotNull @RequestBody Map<UUID, Integer> products);

    /**
     * Предоставить адрес склада для расчёта доставки.
     */
    @GetMapping("/api/v1/warehouse/address")
    AddressDto getWarehouseAddress();
}
