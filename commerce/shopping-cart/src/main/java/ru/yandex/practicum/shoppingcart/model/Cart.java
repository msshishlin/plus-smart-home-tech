package ru.yandex.practicum.shoppingcart.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;
import java.util.UUID;

/**
 * Корзина товаров в онлайн магазине.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "cart")
@ToString
public class Cart {
    /**
     * Идентификатор корзины.
     */
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID cartId;

    /**
     * Имя пользователя.
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * Признак, что корзина активна.
     */
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    /**
     * Коллекция товаров в корзине.
     */
    @CollectionTable(name = "products", joinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "cart_id")})
    @Column(name = "quantity", nullable = false)
    @ElementCollection
    @MapKeyColumn(name = "product_id")
    private Map<UUID, Integer> products;
}
