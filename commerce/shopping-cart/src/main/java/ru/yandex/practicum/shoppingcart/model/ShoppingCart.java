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
@Table(name = "shopping_carts")
@ToString
public class ShoppingCart {
    /**
     * Идентификатор корзины.
     */
    @Column(name = "shopping_cart_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID shoppingCartId;

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
     * Отображение идентификатора товара на количество в корзине.
     */
    @CollectionTable(name = "products", joinColumns = {@JoinColumn(name = "shopping_cart_id", referencedColumnName = "shopping_cart_id")})
    @Column(name = "quantity", nullable = false)
    @ElementCollection
    @MapKeyColumn(name = "product_id")
    private Map<UUID, Integer> products;
}
