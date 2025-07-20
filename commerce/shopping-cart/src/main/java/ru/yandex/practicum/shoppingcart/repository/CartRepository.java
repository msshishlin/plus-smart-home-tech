package ru.yandex.practicum.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.shoppingcart.model.Cart;

import java.util.Optional;
import java.util.UUID;

/**
 * Контракт хранилища данных для корзин пользователей.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUsernameAndIsActive(String userName, boolean isActive);
}
