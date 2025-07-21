package ru.yandex.practicum.interactionapi.dto.shoppingstore;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Товар, продаваемый в интернет-магазине.
 */
@Builder(toBuilder = true)
@Data
public class ProductDto {
    /**
     * Идентификатор товара в БД.
     */
    private UUID productId;

    /**
     * Наименование товара.
     */
    @NotEmpty
    private String productName;

    /**
     * Описание товара.
     */
    @NotEmpty
    private String description;

    /**
     * Ссылка на картинку во внешнем хранилище или SVG.
     */
    private String imageSrc;

    /**
     * Статус, перечисляющий состояние остатка как свойство товара
     */
    @NotNull
    private QuantityState quantityState;

    /**
     * Статус товара.
     */
    @NotNull
    private ProductState productState;

    /**
     * Категория товара.
     */
    private ProductCategory productCategory;

    /**
     * Цена товара.
     */
    @NotNull
    @Positive
    private float price;
}
