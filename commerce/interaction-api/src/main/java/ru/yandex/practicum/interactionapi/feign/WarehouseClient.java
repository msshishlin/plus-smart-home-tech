package ru.yandex.practicum.interactionapi.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.interactionapi.dto.AddressDto;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.interactionapi.exception.warehouse.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.interactionapi.exception.warehouse.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.interactionapi.exception.warehouse.SpecifiedProductAlreadyInWarehouseException;

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
     * @throws ProductInShoppingCartLowQuantityInWarehouseException товар из корзины не находится в требуемом количестве на складе.
     */
    @PostMapping("/api/v1/warehouse/check")
    BookedProductsDto checkProductQuantity(@RequestBody @Valid ShoppingCartDto shoppingCartDto) throws ProductInShoppingCartLowQuantityInWarehouseException;

    /**
     * Предоставить адрес склада для расчёта доставки.
     */
    @GetMapping("/api/v1/warehouse/address")
    AddressDto getWarehouseAddress();
}
