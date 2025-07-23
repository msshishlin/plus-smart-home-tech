package ru.yandex.practicum.shoppingcart.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;
import ru.yandex.practicum.shoppingcart.model.ShoppingCart;

/**
 * Маппер для сущности корзины товаров.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShoppingCartMapper {
    /**
     * Преобразовать сущность корзины товаров в трансферный объект корзины товаров.
     *
     * @param shoppingCart сущности корзины товаров.
     * @return трансферный объект корзины товаров.
     */
    ShoppingCartDto mapToCartDto(ShoppingCart shoppingCart);
}
