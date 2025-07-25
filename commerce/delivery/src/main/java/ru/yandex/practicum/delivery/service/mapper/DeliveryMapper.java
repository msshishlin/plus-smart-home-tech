package ru.yandex.practicum.delivery.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.delivery.model.Delivery;
import ru.yandex.practicum.interactionapi.dto.delivery.DeliveryDto;

/**
 * Маппер для сущности доставки.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeliveryMapper {
    /**
     * Преобразовать трансферный объект доставки в сущность доставки.
     *
     * @param deliveryDto трансферный объект доставки.
     * @return сущность доставки.
     */
    @Mapping(target = "fromAddress", ignore = true)
    @Mapping(target = "toAddress", ignore = true)
    Delivery mapToDelivery(DeliveryDto deliveryDto);

    /**
     * Преобразовать сущность доставки в трансферный объект доставки.
     *
     * @param delivery сущность доставки.
     * @return трансферный объект доставки.
     */
    DeliveryDto mapToDeliveryDto(Delivery delivery);
}
