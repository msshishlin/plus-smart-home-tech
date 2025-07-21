package ru.yandex.practicum.warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interactionapi.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.model.Product;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(target = "width", source = "dimension.width")
    @Mapping(target = "height", source = "dimension.height")
    @Mapping(target = "depth", source = "dimension.depth")
    Product mapToProduct(NewProductInWarehouseRequest newProductInWarehouseRequest);

    @Mapping(target = "dimension.width", source = "width")
    @Mapping(target = "dimension.height", source = "height")
    @Mapping(target = "dimension.depth", source = "depth")
    NewProductInWarehouseRequest mapToProductDto(Product product);
}
