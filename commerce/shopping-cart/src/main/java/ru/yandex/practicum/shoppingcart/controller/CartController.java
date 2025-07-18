package ru.yandex.practicum.shoppingcart.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shoppingcart.dto.CartDto;
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
    private CartService cartService;

    /**
     * Получить корзину пользователя.
     *
     * @param userName имя пользователя.
     * @return корзина пользователя.
     */
    @GetMapping
    public CartDto getCart(@NotEmpty @RequestParam String userName) {
        return cartService.getCart(userName);
    }

    /**
     * Добавить товары в корзину пользователя.
     *
     * @param userName имя пользователя.
     * @param products список товаров.
     * @return корзина пользователя.
     */
    @PutMapping
    public CartDto addProductsToCart(@NotEmpty @RequestParam String userName,
                                     @NotNull @RequestBody Map<UUID, Integer> products) {
        return cartService.addProductsToCart(userName, products);
    }

    /**
     * Изменить количество товара в корзине пользователя.
     *
     * @param userName имя пользователя.
     * @param request  Запрос на изменение количества товара в корзине.
     * @return корзина пользователя.
     */
    @PostMapping("/change-quantity")
    public CartDto changeProductQuantity(@NotEmpty @RequestParam String userName,
                                         @RequestBody @Valid ChangeProductQuantityRequest request) {
        return cartService.changeProductQuantity(userName, request);
    }

    /**
     * Удалить товары из корзины пользователя.
     *
     * @param userName   имя пользователя.
     * @param productIds идентификаторы удаляемых продуктов.
     * @return корзина пользователя.
     */
    @PostMapping("/remove")
    public CartDto removeProductsFromCart(@NotEmpty @RequestParam String userName,
                                          @NotNull @RequestBody Collection<UUID> productIds) {
        return cartService.removeProductsFromCart(userName, productIds);
    }

    /**
     * Деактивировать корзину пользователя.
     *
     * @param userName имя пользователя.
     */
    @DeleteMapping
    public void deactivateCart(@NotEmpty @RequestParam String userName) {
        cartService.deactivateCart(userName);
    }
}
