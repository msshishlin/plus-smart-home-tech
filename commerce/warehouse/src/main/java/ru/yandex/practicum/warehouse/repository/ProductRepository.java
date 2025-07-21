package ru.yandex.practicum.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.warehouse.model.Product;

import java.util.UUID;

/**
 * Контракт хранилища данных для товаров на складе.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
