package ru.yandex.practicum.interactionapi.exception.shoppingcart;

/**
 * Исключение, выбрасываемое сервисом, если в корзине пользователя нет товаров, над которыми необходимо провести действия.
 */
public class NoProductsInShoppingCartException extends RuntimeException {
    /**
     * Конструктор.
     */
    public NoProductsInShoppingCartException() {
        super("Нет искомых товаров в корзине");
    }
}
