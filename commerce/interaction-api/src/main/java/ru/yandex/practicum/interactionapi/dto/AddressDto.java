package ru.yandex.practicum.interactionapi.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Представление адреса в системе.
 */
@Builder(toBuilder = true)
@Data
public class AddressDto {
    /**
     * Страна.
     */
    private String country;

    /**
     * Город.
     */
    private String city;

    /**
     * Улица.
     */
    private String street;

    /**
     * Дом.
     */
    private String house;

    /**
     * Квартира.
     */
    private String flat;
}
