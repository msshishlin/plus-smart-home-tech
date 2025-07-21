package ru.yandex.practicum.interactionapi.feign;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.CartDto;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ChangeProductQuantityRequest;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "shopping-cart")
public interface ShoppingCartClient {
    /**
     * Получить корзину пользователя.
     *
     * @param username имя пользователя.
     * @return корзина пользователя.
     */
    @GetMapping("/api/v1/shopping-cart")
    CartDto getCart(@NotEmpty @RequestParam String username);

    /**
     * Добавить товары в корзину пользователя.
     *
     * @param username имя пользователя.
     * @param products список товаров.
     * @return корзина пользователя.
     */
    @PutMapping("/api/v1/shopping-cart")
    CartDto addProductsToCart(@NotEmpty @RequestParam String username, @NotNull @RequestBody Map<UUID, Integer> products);

    /**
     * Изменить количество товара в корзине пользователя.
     *
     * @param username имя пользователя.
     * @param request  Запрос на изменение количества товара в корзине.
     * @return корзина пользователя.
     */
    @PostMapping("/api/v1/shopping-cart/change-quantity")
    CartDto changeProductQuantity(@NotEmpty @RequestParam String username, @RequestBody @Valid ChangeProductQuantityRequest request);

    /**
     * Удалить товары из корзины пользователя.
     *
     * @param username   имя пользователя.
     * @param productIds идентификаторы удаляемых продуктов.
     * @return корзина пользователя.
     */
    @PostMapping("/api/v1/shopping-cart/remove")
    CartDto removeProductsFromCart(@NotEmpty @RequestParam String username, @NotNull @RequestBody Collection<UUID> productIds);

    /**
     * Деактивировать корзину пользователя.
     *
     * @param username имя пользователя.
     */
    @DeleteMapping("/api/v1/shopping-cart")
    void deactivateCart(@NotEmpty @RequestParam String username);
}
