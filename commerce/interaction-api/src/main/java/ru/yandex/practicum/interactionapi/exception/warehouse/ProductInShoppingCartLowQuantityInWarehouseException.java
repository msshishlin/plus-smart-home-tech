package ru.yandex.practicum.interactionapi.exception.warehouse;

/**
 * Исключение, выбрасываемое сервисом, если товара на складе меньше, чем в корзине товаров пользователя.
 */
public class ProductInShoppingCartLowQuantityInWarehouseException extends RuntimeException {
    /**
     * Констуктор.
     */
    public ProductInShoppingCartLowQuantityInWarehouseException() {
        super("Товар из корзины не находится в требуемом количестве на складе");
    }
}
