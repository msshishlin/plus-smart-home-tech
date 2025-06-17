package ru.yandex.practicum.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.analyzer.model.Sensor;

import java.util.Collection;
import java.util.Optional;

/**
 * Контракт хранилища датчиков.
 */
public interface SensorRepository extends JpaRepository<Sensor, String> {
    /**
     * Проверить наличие регистрации датчика в хабе.
     *
     * @param id    идентификатор датчика.
     * @param hubId идентификатор хаба.
     * @return признак зарегистрирован ли датчик в хабе.
     */
    boolean existsByIdAndHubId(String id, String hubId);

    /**
     * Проверить наличие регистрации датчиков в хабе.
     *
     * @param ids   коллекция идентификаторов датчиков.
     * @param hubId идентификатор хаба.
     * @return признак все ли датчики зарегистрированы в хабе.
     */
    boolean existsByIdInAndHubId(Collection<String> ids, String hubId);

    /**
     * Найти датчик по его идентификатору и идентификатору хаба, в котором он зарегистрирован.
     *
     * @param id    идентификатор датчика.
     * @param hubId идентификатор хаба.
     * @return датчик.
     */
    Optional<Sensor> findByIdAndHubId(String id, String hubId);

    /**
     * Удалить датчик по его идентификатору и идентификатору хаба, в котором он зарегистрирован.
     *
     * @param id    идентификатор датчика.
     * @param hubId идентификатор хаба.
     */
    void deleteByIdAndHubId(String id, String hubId);
}
