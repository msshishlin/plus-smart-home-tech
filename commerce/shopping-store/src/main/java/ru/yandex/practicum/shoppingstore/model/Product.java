package ru.yandex.practicum.shoppingstore.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductCategory;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductState;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.QuantityState;

import java.util.UUID;

/**
 * Товар, продаваемый в интернет-магазине.
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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID productId;

    /**
     * Наименование товара.
     */
    @Column(name = "product_name", nullable = false)
    private String productName;

    /**
     * Описание товара.
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Ссылка на картинку во внешнем хранилище или SVG.
     */
    @Column(name = "image_src")
    private String imageSrc;

    /**
     * Статус, перечисляющий состояние остатка как свойство товара
     */
    @Column(name = "quantity_state", nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcType(value = PostgreSQLEnumJdbcType.class)
    private QuantityState quantityState;

    /**
     * Статус товара.
     */
    @Column(name = "product_state", nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcType(value = PostgreSQLEnumJdbcType.class)
    private ProductState productState;

    /**
     * Категория товара.
     */
    @Column(name = "product_category")
    @Enumerated(EnumType.STRING)
    @JdbcType(value = PostgreSQLEnumJdbcType.class)
    private ProductCategory productCategory;

    /**
     * Цена товара.
     */
    @Column(name = "price")
    private float price;
}
