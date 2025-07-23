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
import ru.yandex.practicum.interactionapi.exception.shoppingstore.ProductNotFoundException;

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
     * @throws ProductNotFoundException товар с идентификатором {@code productId} не найден.
     */
    @GetMapping("/api/v1/shopping-store/{productId}")
    ProductDto findById(@PathVariable UUID productId) throws ProductNotFoundException;

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
     * @throws ProductNotFoundException товар с идентификатором {@code productDto.productId} не найден.
     */
    @PostMapping("/api/v1/shopping-store")
    ProductDto updateProduct(@RequestBody @Valid ProductDto productDto) throws ProductNotFoundException;

    /**
     * Изменить статус остатка товара.
     *
     * @param productId     идентификатор товара.
     * @param quantityState новый статус остатка товара.
     * @return признак успешности обновления статуса остатка товара.
     * @throws ProductNotFoundException товар с идентификатором {@code productId} не найден.
     */
    @PostMapping("/api/v1/shopping-store/quantityState")
    boolean setQuantityState(@RequestParam UUID productId, @RequestParam QuantityState quantityState) throws ProductNotFoundException;

    /**
     * Удаление товара.
     *
     * @param productId товар, продаваемый в интернет-магазине.
     * @return признак успешности удаления товара.
     * @throws ProductNotFoundException товар с идентификатором {@code productId} не найден.
     */
    @PostMapping("/api/v1/shopping-store/removeProductFromStore")
    boolean removeProduct(@RequestBody @Valid UUID productId) throws ProductNotFoundException;
}
