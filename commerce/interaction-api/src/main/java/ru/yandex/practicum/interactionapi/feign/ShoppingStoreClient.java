package ru.yandex.practicum.interactionapi.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductCategory;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductDto;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.QuantityState;

import java.util.UUID;

@FeignClient(name = "shopping-store")
public interface ShoppingStoreClient {
    /**
     * Создание нового товара.
     *
     * @param productDto товар, продаваемый в интернет-магазине.
     * @return товар, продаваемый в интернет-магазине.
     */
    @PutMapping("/api/v1/shopping-store")
    ProductDto createProduct(@RequestBody @Valid ProductDto productDto);

    /**
     * Получить сведения по товару из БД.
     *
     * @param productId идентификатор товара.
     * @return товар, продаваемый в интернет-магазине.
     */
    @GetMapping("/api/v1/shopping-store/{productId}")
    ProductDto findById(@PathVariable UUID productId);

    /**
     * Найти все товары, относящиеся к определенной категории.
     *
     * @param productCategory категория товара.
     * @param pageable        параметры пагинации.
     * @return коллекция товаров.
     */
    @GetMapping("/api/v1/shopping-store")
    Page<ProductDto> findByProductCategory(@RequestParam(name = "category") ProductCategory productCategory, @PageableDefault Pageable pageable);

    /**
     * Обновление товара.
     *
     * @param productDto товар, продаваемый в интернет-магазине.
     * @return товар, продаваемый в интернет-магазине.
     */
    @PostMapping("/api/v1/shopping-store")
    ProductDto updateProduct(@RequestBody @Valid ProductDto productDto);

    /**
     * Изменить статус остатка товара.
     *
     * @param productId     идентификатор товара.
     * @param quantityState новый статус остатка товара.
     * @return признак успешности обновления статуса остатка товара.
     */
    @PostMapping("/api/v1/shopping-store/quantityState")
    boolean setQuantityState(@RequestParam UUID productId, @RequestParam QuantityState quantityState);

    /**
     * Удаление товара.
     *
     * @param productId товар, продаваемый в интернет-магазине.
     * @return признак успешности удаления товара.
     */
    @PostMapping("/api/v1/shopping-store/removeProductFromStore")
    boolean removeProduct(@RequestBody @Valid UUID productId);
}
