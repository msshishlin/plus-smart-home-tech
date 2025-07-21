package ru.yandex.practicum.shoppingstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductDto;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductCategory;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.QuantityState;

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
     */
    ProductDto findById(UUID productId);

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
     */
    ProductDto updateProduct(ProductDto productDto);

    /**
     * Изменить статус остатка товара.
     *
     * @param productId     идентификатор товара.
     * @param quantityState новый статус остатка товара.
     * @return признак успешности обновления статуса остатка товара.
     */
    boolean setQuantityState(UUID productId, QuantityState quantityState);

    /**
     * Удалить товар.
     *
     * @param productId идентификатор товара.
     * @return признак успешности удаления товара.
     */
    boolean deleteProduct(UUID productId);
}
