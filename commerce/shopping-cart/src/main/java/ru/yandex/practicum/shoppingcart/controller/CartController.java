package ru.yandex.practicum.shoppingcart.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.interactionapi.dto.CartDto;
import ru.yandex.practicum.shoppingcart.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.shoppingcart.service.CartService;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Контроллер для работы с корзинами пользователей.
 */
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
@RestController
@Slf4j
public class CartController {
    /**
     * Сервис для работы с корзинами пользователей.
     */
    private final CartService cartService;

    /**
     * Получить корзину пользователя.
     *
     * @param username имя пользователя.
     * @return корзина пользователя.
     */
    @GetMapping
    public CartDto getCart(@NotEmpty @RequestParam String username) {
        return cartService.getCart(username);
    }

    /**
     * Добавить товары в корзину пользователя.
     *
     * @param username имя пользователя.
     * @param products список товаров.
     * @return корзина пользователя.
     */
    @PutMapping
    public CartDto addProductsToCart(@NotEmpty @RequestParam String username,
                                     @NotNull @RequestBody Map<UUID, Integer> products) {
        return cartService.addProductsToCart(username, products);
    }

    /**
     * Изменить количество товара в корзине пользователя.
     *
     * @param username имя пользователя.
     * @param request  Запрос на изменение количества товара в корзине.
     * @return корзина пользователя.
     */
    @PostMapping("/change-quantity")
    public CartDto changeProductQuantity(@NotEmpty @RequestParam String username,
                                         @RequestBody @Valid ChangeProductQuantityRequest request) {
        return cartService.changeProductQuantity(username, request);
    }

    /**
     * Удалить товары из корзины пользователя.
     *
     * @param username   имя пользователя.
     * @param productIds идентификаторы удаляемых продуктов.
     * @return корзина пользователя.
     */
    @PostMapping("/remove")
    public CartDto removeProductsFromCart(@NotEmpty @RequestParam String username,
                                          @NotNull @RequestBody Collection<UUID> productIds) {
        return cartService.removeProductsFromCart(username, productIds);
    }

    /**
     * Деактивировать корзину пользователя.
     *
     * @param username имя пользователя.
     */
    @DeleteMapping
    public void deactivateCart(@NotEmpty @RequestParam String username) {
        cartService.deactivateCart(username);
    }
}
