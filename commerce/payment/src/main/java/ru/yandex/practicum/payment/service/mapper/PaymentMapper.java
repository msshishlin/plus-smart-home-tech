package ru.yandex.practicum.payment.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interactionapi.dto.payment.PaymentDto;
import ru.yandex.practicum.payment.model.Payment;

/**
 * Маппер для сущности оплаты.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
    /**
     * Преобразовать сущности оплаты в трансферный объект оплаты.
     *
     * @param payment сущность оплаты.
     * @return трансферный объект оплаты.
     */
    PaymentDto mapToPaymentDto(Payment payment);
}
