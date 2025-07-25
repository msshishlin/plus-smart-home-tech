package ru.yandex.practicum.shoppingcart.service;

import ru.yandex.practicum.interactionapi.dto.shoppingcart.ChangeProductQuantityRequest;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.exception.shoppingcart.NoProductsInShoppingCartException;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Контракт сервиса для работы с корзинами пользователей.
 */
public interface ShoppingCartService {
    /**
     * Получить корзину товаров пользователя.
     *
     * @param username имя пользователя.
     * @return корзина пользователя.
     */
    ShoppingCartDto getShoppingCart(String username);

    /**
     * Добавить товары в корзину пользователя.
     *
     * @param username имя пользователя.
     * @param products список товаров.
     * @return корзина пользователя.
     */
    ShoppingCartDto addProductsToShoppingCart(String username, Map<UUID, Integer> products);

    /**
     * Изменить количество товара в корзине пользователя.
     *
     * @param username имя пользователя.
     * @param request  Запрос на изменение количества товара в корзине.
     * @return корзина пользователя.
     * @throws NoProductsInShoppingCartException в корзине отсутствуют товары, количество которых необходимо изменить.
     */
    ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest request) throws NoProductsInShoppingCartException;

    /**
     * Удалить товары из корзины пользователя.
     *
     * @param username   имя пользователя.
     * @param productIds идентификаторы удаляемых продуктов.
     * @return корзина пользователя.
     * @throws NoProductsInShoppingCartException в корзине отсутствуют товары, которые необходимо удалить.
     */
    ShoppingCartDto removeProductsFromShoppingCart(String username, Collection<UUID> productIds) throws NoProductsInShoppingCartException;

    /**
     * Деактивировать корзину товаров пользователя.
     *
     * @param username имя пользователя.
     */
    void deactivateShoppingCart(String username);
}
