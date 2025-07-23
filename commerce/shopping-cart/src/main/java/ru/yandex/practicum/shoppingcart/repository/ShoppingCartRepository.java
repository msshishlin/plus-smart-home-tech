package ru.yandex.practicum.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.shoppingcart.model.ShoppingCart;

import java.util.Optional;
import java.util.UUID;

/**
 * Контракт хранилища данных для корзин товаров.
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {
    /**
     * Найти активную корзину товаров по имени пользователя.
     *
     * @param userName имя пользователя.
     * @return корзина товаров.
     */
    Optional<ShoppingCart> findByUsernameAndIsActiveTrue(String userName);
}
