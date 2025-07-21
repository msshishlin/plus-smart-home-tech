package ru.yandex.practicum.shoppingcart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.CartDto;
import ru.yandex.practicum.shoppingcart.model.Cart;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartMapper {
    @Mapping(target = "shoppingCartId", source = "cartId")
    CartDto mapToCartDto(Cart cart);
}
