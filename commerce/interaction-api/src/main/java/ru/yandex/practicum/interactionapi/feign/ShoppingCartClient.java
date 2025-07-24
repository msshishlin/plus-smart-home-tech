package ru.yandex.practicum.interactionapi.feign;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ChangeProductQuantityRequest;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.exception.shoppingcart.NoProductsInShoppingCartException;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Контракт клиента для сервиса корзины товаров.
 */
@FeignClient(name = "shopping-cart")
public interface ShoppingCartClient {
    /**
     * Получить корзину товаров пользователя.
     *
     * @param username имя пользователя.
     * @return корзина пользователя.
     */
    @GetMapping("/api/v1/shopping-cart")
    ShoppingCartDto getShoppingCart(@NotEmpty @RequestParam String username);

    /**
     * Добавить товары в корзину пользователя.
     *
     * @param username имя пользователя.
     * @param products список товаров.
     * @return корзина пользователя.
     */
    @PutMapping("/api/v1/shopping-cart")
    ShoppingCartDto addProductsToShoppingCart(@NotEmpty @RequestParam String username, @NotNull @RequestBody Map<UUID, Integer> products);

    /**
     * Изменить количество товара в корзине пользователя.
     *
     * @param username имя пользователя.
     * @param request  Запрос на изменение количества товара в корзине.
     * @return корзина пользователя.
     * @throws NoProductsInShoppingCartException в корзине отсутствуют товары, количество которых необходимо изменить.
     */
    @PostMapping("/api/v1/shopping-cart/change-quantity")
    ShoppingCartDto changeProductQuantity(@NotEmpty @RequestParam String username, @RequestBody @Valid ChangeProductQuantityRequest request) throws NoProductsInShoppingCartException;

    /**
     * Удалить товары из корзины пользователя.
     *
     * @param username   имя пользователя.
     * @param productIds идентификаторы удаляемых продуктов.
     * @return корзина пользователя.
     * @throws NoProductsInShoppingCartException в корзине отсутствуют товары, которые необходимо удалить.
     */
    @PostMapping("/api/v1/shopping-cart/remove")
    ShoppingCartDto removeProductsFromShoppingCart(@NotEmpty @RequestParam String username, @NotNull @RequestBody Collection<UUID> productIds) throws NoProductsInShoppingCartException;

    /**
     * Деактивировать корзину товаров пользователя.
     *
     * @param username имя пользователя.
     */
    @DeleteMapping("/api/v1/shopping-cart")
    void deactivateShoppingCart(@NotEmpty @RequestParam String username);
}
