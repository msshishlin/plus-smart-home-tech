package ru.yandex.practicum.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.payment.model.Payment;

import java.util.UUID;

/**
 * Контракт хранилища данных об оплатах.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
