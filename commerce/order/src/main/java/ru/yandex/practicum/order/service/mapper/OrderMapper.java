package ru.yandex.practicum.order.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.order.model.Order;

/**
 * Маппер для сущности заказа.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    /**
     * Преобразовать сущность заказа в трансферный объект заказа.
     *
     * @param order сущность заказа.
     * @return трансферный объект заказа.
     */
    OrderDto mapToOrderDto(Order order);
}
