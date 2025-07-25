package ru.yandex.practicum.delivery.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Адрес.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "addresses")
@ToString
public class Address {
    /**
     * Идентификатор адреса.
     */
    @Column(name = "address_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID addressId;

    /**
     * Страна.
     */
    @Column(name = "country")
    private String country;

    /**
     * Город.
     */
    @Column(name = "city")
    private String city;

    /**
     * Улица.
     */
    @Column(name = "street")
    private String street;

    /**
     * Дом.
     */
    @Column(name = "house")
    private String house;

    /**
     * Квартира.
     */
    @Column(name = "flat")
    private String flat;
}
