package ru.yandex.practicum.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.order.model.Order;

import java.util.UUID;

/**
 * Контракт хранилища данных для заказов.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

}
