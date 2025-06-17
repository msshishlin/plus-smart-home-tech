package ru.yandex.practicum.aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.aggregator.model.Scenario;

import java.util.List;
import java.util.Optional;

/**
 * Контракт хранилища сценариев.
 */
public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    /**
     * Поиск сценариев по идентификатору хаба.
     *
     * @param hubId идентификатор хаба.
     * @return список сценариев.
     */
    List<Scenario> findByHubId(String hubId);

    /**
     * Поиск сценария по названию сценария и идентификатору хаба.
     *
     * @param name  название сценария.
     * @param hubId идентификатор хаба.
     * @return сценарий.
     */
    Optional<Scenario> findByNameAndHubId(String name, String hubId);
}
