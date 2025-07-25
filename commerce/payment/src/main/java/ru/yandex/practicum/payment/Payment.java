package ru.yandex.practicum.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Основной класс сервиса оплаты заказов, содержащий точку входа в приложение.
 */
@ConfigurationPropertiesScan
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"ru.yandex.practicum.interactionapi.feign"})
@SpringBootApplication
public class Payment {
    /**
     * Точка входа в приложение.
     *
     * @param args набор аргументов, с которыми запускается приложение.
     */
    public static void main(String[] args) {
        SpringApplication.run(Payment.class, args);
    }
}
