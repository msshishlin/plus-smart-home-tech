package ru.yandex.practicum.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ChangeProductQuantityRequest;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.exception.shoppingcart.NoProductsInShoppingCartException;
import ru.yandex.practicum.interactionapi.feign.ShoppingCartClient;
import ru.yandex.practicum.shoppingcart.service.ShoppingCartService;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Контроллер для работы с корзинами товаров.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class ShoppingCartController implements ShoppingCartClient {
    /**
     * Сервис для работы с корзинами товаров.
     */
    private final ShoppingCartService shoppingCartService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ShoppingCartDto getShoppingCart(String username) {
        log.info("Getting shopping cart for user {}", username);
        return shoppingCartService.getShoppingCart(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShoppingCartDto addProductsToShoppingCart(String username, Map<UUID, Integer> products) {
        log.info("Adding products {} to user {} shopping cart", products, username);
        return shoppingCartService.addProductsToShoppingCart(username, products);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest request) throws NoProductsInShoppingCartException {
        log.info("Change product quantity {} in user {} shopping cart", request, username);
        return shoppingCartService.changeProductQuantity(username, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShoppingCartDto removeProductsFromShoppingCart(String username, Collection<UUID> productIds) throws NoProductsInShoppingCartException {
        log.info("Removing products {} from user {} shopping cart", productIds, username);
        return shoppingCartService.removeProductsFromShoppingCart(username, productIds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deactivateShoppingCart(String username) {
        log.info("Deactivating shopping cart for user {}", username);
        shoppingCartService.deactivateShoppingCart(username);
    }
}
