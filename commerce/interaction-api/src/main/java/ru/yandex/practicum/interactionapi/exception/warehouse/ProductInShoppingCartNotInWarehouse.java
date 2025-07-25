package ru.yandex.practicum.interactionapi.exception.warehouse;

/**
 * Исключение, выбрасываемое сервисом, если товары, указанные в корзине отсутствуют на складе.
 */
public class ProductInShoppingCartNotInWarehouse extends RuntimeException {
    /**
     * Конструктор.
     */
    public ProductInShoppingCartNotInWarehouse() {
        super("Товары из корзины отсутствует в БД склада");
    }
}
