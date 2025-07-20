package ru.yandex.practicum.shoppingcart.service;

import ru.yandex.practicum.interactionapi.dto.CartDto;
import ru.yandex.practicum.shoppingcart.dto.ChangeProductQuantityRequest;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Контракт сервиса для работы с корзинами пользователей.
 */
public interface CartService {
    /**
     * Получить корзину пользователя.
     *
     * @param username имя пользователя.
     * @return корзина пользователя.
     */
    CartDto getCart(String username);

    /**
     * Добавить товары в корзину пользователя.
     *
     * @param username имя пользователя.
     * @param products список товаров.
     * @return корзина пользователя.
     */
    CartDto addProductsToCart(String username, Map<UUID, Integer> products);

    /**
     * Изменить количество товара в корзине пользователя.
     *
     * @param username имя пользователя.
     * @param request  Запрос на изменение количества товара в корзине.
     * @return корзина пользователя.
     */
    CartDto changeProductQuantity(String username, ChangeProductQuantityRequest request);

    /**
     * Удалить товары из корзины пользователя.
     *
     * @param username   имя пользователя.
     * @param productIds идентификаторы удаляемых продуктов.
     * @return корзина пользователя.
     */
    CartDto removeProductsFromCart(String username, Collection<UUID> productIds);

    /**
     * Деактивировать корзину пользователя.
     *
     * @param username имя пользователя.
     */
    void deactivateCart(String username);
}
