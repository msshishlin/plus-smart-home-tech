package ru.yandex.practicum.payment.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация сервиса оплаты заказов.
 */
@Configuration
@ConfigurationProperties("ru.yandex.practicum.payment")
@Getter
@Setter
@ToString
public class PaymentConfiguration {
    /**
     * Стоимость доставки.
     */
    private float deliveryCost;

    /**
     * НДС.
     */
    private int valueAddedTax;
}
