package ru.yandex.practicum.shoppingstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductCategory;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductDto;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.QuantityState;
import ru.yandex.practicum.interactionapi.exception.shoppingstore.ProductNotFoundException;

import java.util.UUID;

/**
 * Контракт сервиса для работы с товарами.
 */
public interface ProductService {
    /**
     * Создание нового товара.
     *
     * @param productDto товар, продаваемый в интернет-магазине.
     * @return товар, продаваемый в интернет-магазине.
     */
    ProductDto createProduct(ProductDto productDto);

    /**
     * Получить сведения по товару из БД.
     *
     * @param productId идентификатор товара в БД.
     * @return товар, продаваемый в интернет-магазине.
     * @throws ProductNotFoundException товар с идентификатором {@code productId} не найден.
     */
    ProductDto findById(UUID productId) throws ProductNotFoundException;

    /**
     * Найти все товары, относящиеся к определенной категории.
     *
     * @param productCategory категория товара.
     * @param pageable        параметры пагинации.
     * @return коллекция товаров.
     */
    Page<ProductDto> findByProductCategory(ProductCategory productCategory, Pageable pageable);

    /**
     * Обновить товар.
     *
     * @param productDto товар, продаваемый в интернет-магазине.
     * @return товар, продаваемый в интернет-магазине.
     * @throws ProductNotFoundException товар с идентификатором {@code productDto.productId} не найден.
     */
    ProductDto updateProduct(ProductDto productDto) throws ProductNotFoundException;

    /**
     * Изменить статус остатка товара.
     *
     * @param productId     идентификатор товара.
     * @param quantityState новый статус остатка товара.
     * @return признак успешности обновления статуса остатка товара.
     * @throws ProductNotFoundException товар с идентификатором {@code productId} не найден.
     */
    boolean setQuantityState(UUID productId, QuantityState quantityState) throws ProductNotFoundException;

    /**
     * Удалить товар.
     *
     * @param productId идентификатор товара.
     * @return признак успешности удаления товара.
     * @throws ProductNotFoundException товар с идентификатором {@code productId} не найден.
     */
    boolean deleteProduct(UUID productId) throws ProductNotFoundException;
}
