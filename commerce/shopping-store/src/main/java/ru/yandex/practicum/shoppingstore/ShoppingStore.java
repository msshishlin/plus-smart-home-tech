package ru.yandex.practicum.shoppingstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Основной класс сервиса витрины товаров, содержащий точку входа в приложение.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ShoppingStore {
    /**
     * Точка входа в приложение.
     *
     * @param args набор аргументов, с которыми запускается приложение.
     */
    public static void main(String[] args) {
        SpringApplication.run(ShoppingStore.class, args);
    }
}
