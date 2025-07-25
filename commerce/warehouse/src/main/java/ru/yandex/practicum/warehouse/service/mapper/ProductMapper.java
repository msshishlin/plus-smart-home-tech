package ru.yandex.practicum.warehouse.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interactionapi.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.model.Product;

/**
 * Маппер для сущности товара на складе.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    /**
     * Преобразовать запрос на добавление нового товара на склад в сущность товара.
     *
     * @param newProductInWarehouseRequest запрос на добавление нового товара на склад.
     * @return сущность товара.
     */
    @Mapping(target = "width", source = "dimension.width")
    @Mapping(target = "height", source = "dimension.height")
    @Mapping(target = "depth", source = "dimension.depth")
    Product mapToProduct(NewProductInWarehouseRequest newProductInWarehouseRequest);
}
