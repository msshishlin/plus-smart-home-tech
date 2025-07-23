package ru.yandex.practicum.shoppingcart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ChangeProductQuantityRequest;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.exception.shoppingcart.NoProductsInShoppingCartException;
import ru.yandex.practicum.interactionapi.feign.WarehouseClient;
import ru.yandex.practicum.shoppingcart.model.ShoppingCart;
import ru.yandex.practicum.shoppingcart.repository.ShoppingCartRepository;
import ru.yandex.practicum.shoppingcart.service.mapper.ShoppingCartMapper;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис для работы с корзинами товаров.
 */
@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    /**
     * Хранилище данных для корзин товаров.
     */
    private final ShoppingCartRepository shoppingCartRepository;

    /**
     * Маппер для сущности корзины товаров.
     */
    private final ShoppingCartMapper cartMapper;

    /**
     * Клиент для склад-сервиса.
     */
    private final WarehouseClient warehouseClient;

    /**
     * Получить активную или создать новую корзину пользователя.
     *
     * @param userName имя пользователя.
     * @return корзина пользователя.
     */
    private ShoppingCart getActiveOrCreateNewCart(String userName) {
        return shoppingCartRepository.findByUsernameAndIsActiveTrue(userName).orElseGet(() -> {
            ShoppingCart newShoppingCart = ShoppingCart.builder()
                    .username(userName)
                    .isActive(true)
                    .build();

            shoppingCartRepository.save(newShoppingCart);
            return newShoppingCart;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShoppingCartDto getShoppingCart(String username) {
        return cartMapper.mapToCartDto(getActiveOrCreateNewCart(username));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShoppingCartDto addProductsToShoppingCart(String username, Map<UUID, Integer> products) {
        ShoppingCart shoppingCart = getActiveOrCreateNewCart(username);
        shoppingCart.setProducts(products);

        warehouseClient.checkProductQuantity(cartMapper.mapToCartDto(shoppingCart));

        shoppingCartRepository.save(shoppingCart);
        return cartMapper.mapToCartDto(shoppingCart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest request) throws NoProductsInShoppingCartException {
        ShoppingCart shoppingCart = getActiveOrCreateNewCart(username);
        if (!shoppingCart.getProducts().containsKey(request.getProductId())) {
            throw new NoProductsInShoppingCartException();
        }

        shoppingCart.getProducts().put(request.getProductId(), request.getNewQuantity());

        shoppingCartRepository.save(shoppingCart);
        return cartMapper.mapToCartDto(shoppingCart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShoppingCartDto removeProductsFromShoppingCart(String username, Collection<UUID> productIds) throws NoProductsInShoppingCartException {
        ShoppingCart shoppingCart = getActiveOrCreateNewCart(username);
        if (!shoppingCart.getProducts().keySet().containsAll(productIds)) {
            throw new NoProductsInShoppingCartException();
        }

        Map<UUID, Integer> filteredProducts = shoppingCart.getProducts()
                .entrySet()
                .stream()
                .filter(product -> productIds.contains(product.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        shoppingCart.setProducts(filteredProducts);

        shoppingCartRepository.save(shoppingCart);
        return cartMapper.mapToCartDto(shoppingCart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deactivateShoppingCart(String username) {
        Optional<ShoppingCart> cartOptional = shoppingCartRepository.findByUsernameAndIsActiveTrue(username);
        if (cartOptional.isEmpty()) {
            return;
        }

        ShoppingCart shoppingCart = cartOptional.get();
        shoppingCart.setActive(false);

        shoppingCartRepository.save(shoppingCart);
    }
}
