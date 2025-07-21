package ru.yandex.practicum.warehouse.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Товар на складе.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "products")
@ToString
public class Product {
    /**
     * Идентификатор товара в БД.
     */
    @Column(name = "product_id", nullable = false)
    @Id
    private UUID productId;

    /**
     * Количество товара на складе.
     */
    @Column(name = "quantity", nullable = false)
    private int quantity;

    /**
     * Признак хрупкости.
     */
    @Column(name = "fragile", nullable = false)
    private boolean fragile;

    /**
     * Ширина.
     */
    @Column(name = "width", nullable = false)
    private float width;

    /**
     * Высота.
     */
    @Column(name = "height", nullable = false)
    private float height;

    /**
     * Глубина.
     */
    @Column(name = "depth", nullable = false)
    private float depth;

    /**
     * Вес товара.
     */
    @Column(name = "weight", nullable = false)
    private float weight;
}
