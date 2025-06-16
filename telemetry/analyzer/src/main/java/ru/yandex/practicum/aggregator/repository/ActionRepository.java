package ru.yandex.practicum.aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.aggregator.model.Action;

/**
 * Контракт хранилища действий.
 */
public interface ActionRepository extends JpaRepository<Action, Long> {
}
