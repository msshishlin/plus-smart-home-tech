package ru.yandex.practicum.interactionapi.dto.payment;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Информация об оплате.
 */
@Builder(toBuilder = true)
@Data
public class PaymentDto {
    /**
     * Идентификатор оплаты.
     */
    private UUID paymentId;

    /**
     * Общая стоимость.
     */
    private float totalPayment;

    /**
     * Стоимость доставки.
     */
    private float deliveryTotal;

    /**
     * Стоимость налога.
     */
    private float feeTotal;
}
