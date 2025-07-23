package ru.yandex.practicum.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.warehouse.model.Product;

import java.util.Optional;
import java.util.UUID;

/**
 * Контракт хранилища данных для товаров на складе.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    /**
     * Найти товар с заданной шириной, высотой, глубиной, весом и признаком хрупкости.
     *
     * @param width   ширина.
     * @param height  высота.
     * @param depth   глубина.
     * @param weight  вес.
     * @param fragile признак хрупкости.
     * @return товар.
     */
    Optional<Product> findByWidthAndHeightAndDepthAndWeightAndFragile(float width, float height, float depth, float weight, boolean fragile);
}
