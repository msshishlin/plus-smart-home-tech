package ru.yandex.practicum.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.analyzer.model.Action;

/**
 * Контракт хранилища действий.
 */
public interface ActionRepository extends JpaRepository<Action, Long> {
}
