package ru.yandex.practicum.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.delivery.model.Address;

import java.util.Optional;
import java.util.UUID;

/**
 * Контракт хранилища адресами.
 */
public interface AddressRepository extends JpaRepository<Address, UUID> {
    /**
     * Найти адрес по стране, городу, улице, номеру дома и квартире.
     *
     * @param country страна.
     * @param city    город.
     * @param street  улица.
     * @param house   дом.
     * @param flat    квартира.
     * @return адрес.
     */
    Optional<Address> findByCountryAndCityAndStreetAndHouseAndFlat(String country, String city, String street, String house, String flat);
}
