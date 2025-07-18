package ru.yandex.practicum.shoppingcart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shoppingcart.dto.CartDto;
import ru.yandex.practicum.shoppingcart.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.shoppingcart.exception.NoProductsInCartException;
import ru.yandex.practicum.shoppingcart.mapper.CartMapper;
import ru.yandex.practicum.shoppingcart.model.Cart;
import ru.yandex.practicum.shoppingcart.repository.CartRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис для работы с корзинами пользователей.
 */
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    /**
     * Хранилище данных для корзин пользователей.
     */
    private final CartRepository cartRepository;

    /**
     * Маппер для сущности корзины пользователя.
     */
    private final CartMapper cartMapper;

    /**
     * Получить активную или создать новую корзину пользователя.
     *
     * @param userName имя пользователя.
     * @return корзина пользователя.
     */
    private Cart getActiveOrCreateNewCart(String userName) {
        return cartRepository.findByUserNameAndIsActive(userName, true).orElseGet(() -> {
            Cart newCart = Cart.builder()
                    .userName(userName)
                    .build();

            cartRepository.save(newCart);
            return newCart;
        });
    }

    @Override
    public CartDto getCart(String userName) {
        return cartMapper.mapToCartDto(getActiveOrCreateNewCart(userName));
    }

    @Override
    public CartDto addProductsToCart(String userName, Map<UUID, Integer> products) {
        Cart cart = getActiveOrCreateNewCart(userName);
        cart.setProducts(products);

        cartRepository.save(cart);
        return cartMapper.mapToCartDto(cart);
    }

    @Override
    public CartDto changeProductQuantity(String userName, ChangeProductQuantityRequest request) {
        Cart cart = getActiveOrCreateNewCart(userName);
        if (!cart.getProducts().containsKey(request.getProductId())) {
            throw new NoProductsInCartException("Нет искомого товаров в корзине");
        }

        cart.getProducts().put(request.getProductId(), request.getNewQuantity());

        cartRepository.save(cart);
        return cartMapper.mapToCartDto(cart);
    }

    @Override
    public CartDto removeProductsFromCart(String userName, Collection<UUID> productIds) {
        Cart cart = getActiveOrCreateNewCart(userName);
        if (!cart.getProducts().keySet().containsAll(productIds)) {
            throw new NoProductsInCartException("Нет искомых товаров в корзине");
        }

        Map<UUID, Integer> filteredProducts = cart.getProducts()
                .entrySet()
                .stream()
                .filter(product -> productIds.contains(product.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        cart.setProducts(filteredProducts);

        cartRepository.save(cart);
        return cartMapper.mapToCartDto(cart);
    }

    @Override
    public void deactivateCart(String userName) {
        Optional<Cart> cartOptional = cartRepository.findByUserNameAndIsActive(userName, true);
        if (cartOptional.isEmpty()) {
            return;
        }

        Cart cart = cartOptional.get();
        cart.setActive(false);

        cartRepository.save(cart);
    }
}
