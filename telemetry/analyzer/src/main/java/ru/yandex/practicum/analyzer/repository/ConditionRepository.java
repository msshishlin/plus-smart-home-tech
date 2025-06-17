package ru.yandex.practicum.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.analyzer.model.Condition;

/**
 * Контракт хранилища условий.
 */
public interface ConditionRepository extends JpaRepository<Condition, Long> {
}
