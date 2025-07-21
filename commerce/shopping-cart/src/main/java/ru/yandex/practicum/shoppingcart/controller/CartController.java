package ru.yandex.practicum.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.CartDto;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ChangeProductQuantityRequest;
import ru.yandex.practicum.interactionapi.feign.ShoppingCartClient;
import ru.yandex.practicum.shoppingcart.service.CartService;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Контроллер для работы с корзинами пользователей.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class CartController implements ShoppingCartClient {
    /**
     * Сервис для работы с корзинами пользователей.
     */
    private final CartService cartService;

    @Override
    public CartDto getCart(String username) {
        return cartService.getCart(username);
    }

    @Override
    public CartDto addProductsToCart(String username, Map<UUID, Integer> products) {
        return cartService.addProductsToCart(username, products);
    }

    @Override
    public CartDto changeProductQuantity(String username, ChangeProductQuantityRequest request) {
        return cartService.changeProductQuantity(username, request);
    }

    @Override
    public CartDto removeProductsFromCart(String username, Collection<UUID> productIds) {
        return cartService.removeProductsFromCart(username, productIds);
    }

    @Override
    public void deactivateCart(String username) {
        cartService.deactivateCart(username);
    }
}
