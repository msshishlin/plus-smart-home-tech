package ru.yandex.practicum.shoppingstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.shoppingstore.dto.ProductDto;
import ru.yandex.practicum.shoppingstore.model.Product;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    Product mapToProduct(ProductDto productDto);

    ProductDto mapToProductDto(Product product);
}
