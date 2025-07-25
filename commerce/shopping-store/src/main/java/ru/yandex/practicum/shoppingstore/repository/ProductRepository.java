package ru.yandex.practicum.shoppingstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductCategory;
import ru.yandex.practicum.shoppingstore.model.Product;

import java.util.UUID;

/**
 * Контракт хранилища данных для товаров.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    /**
     * Найти все товары, относящиеся к определенной категории.
     *
     * @param productCategory категория товара.
     * @param pageable        параметры пагинации.
     * @return коллекция товаров.
     */
    Page<Product> findByProductCategory(ProductCategory productCategory, Pageable pageable);
}
