package ru.yandex.practicum.aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.aggregator.model.Condition;

/**
 * Контракт хранилища условий.
 */
public interface ConditionRepository extends JpaRepository<Condition, Long> {
}
