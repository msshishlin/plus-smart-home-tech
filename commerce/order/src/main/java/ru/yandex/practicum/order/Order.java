package ru.yandex.practicum.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Основной класс сервиса управления заказами покупателя, содержащий точку входа в приложение.
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"ru.yandex.practicum.interactionapi.feign"})
@SpringBootApplication
public class Order {
    /**
     * Точка входа в приложение.
     *
     * @param args набор аргументов, с которыми запускается приложение.
     */
    public static void main(String[] args) {
        SpringApplication.run(Order.class, args);
    }
}
