package ru.yandex.practicum.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.delivery.model.Delivery;

import java.util.UUID;

/**
 * Контракт хранилища данных о доставках.
 */
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
}
