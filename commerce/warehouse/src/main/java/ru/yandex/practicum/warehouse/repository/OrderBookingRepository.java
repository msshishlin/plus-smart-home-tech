package ru.yandex.practicum.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.warehouse.model.OrderBooking;

import java.util.UUID;

/**
 * Контракт хранилища данных о забронированных для заказа товарах.
 */
public interface OrderBookingRepository extends JpaRepository<OrderBooking, UUID> {
}
