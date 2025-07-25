package ru.yandex.practicum.shoppingstore.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductDto;
import ru.yandex.practicum.shoppingstore.model.Product;

/**
 * Маппер для сущности товаров.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    /**
     * Преобразовать трансферный объект товара в сущность товара.
     *
     * @param productDto трансферный объект товара.
     * @return сущность товара.
     */
    Product mapToProduct(ProductDto productDto);

    /**
     * Преобразовать сущность товара в трансферный объект товара.
     *
     * @param product сущность товара.
     * @return трансферный объект товара.
     */
    ProductDto mapToProductDto(Product product);
}
