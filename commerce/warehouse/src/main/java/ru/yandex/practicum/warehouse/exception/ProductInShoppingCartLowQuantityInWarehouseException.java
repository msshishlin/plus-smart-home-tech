package ru.yandex.practicum.warehouse.exception;

public class ProductInShoppingCartLowQuantityInWarehouseException extends RuntimeException {
    public ProductInShoppingCartLowQuantityInWarehouseException() {
        super("Товар из корзины не находится в требуемом количестве на складе");
    }
}
