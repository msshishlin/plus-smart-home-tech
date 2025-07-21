package ru.yandex.practicum.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.CartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.interactionapi.exception.ProductNotFoundException;
import ru.yandex.practicum.interactionapi.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.exception.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.warehouse.mapper.ProductMapper;
import ru.yandex.practicum.warehouse.model.Product;
import ru.yandex.practicum.warehouse.repository.ProductRepository;

import java.util.Collection;

/**
 * Сервис для работы с товарами на складе.
 */
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    /**
     * Хранилище данных для товаров на складе.
     */
    private final ProductRepository productRepository;

    /**
     * Маппер для сущности товара.
     */
    private final ProductMapper productMapper;

    @Override
    public void createProduct(NewProductInWarehouseRequest newProductInWarehouseRequest) {
        Product product = productMapper.mapToProduct(newProductInWarehouseRequest);
        productRepository.save(product);
    }

    @Override
    public void addProduct(AddProductToWarehouseRequest addProductToWarehouseRequest) {
        Product product = productRepository.findById(addProductToWarehouseRequest.getProductId()).orElseThrow(() -> new ProductNotFoundException(addProductToWarehouseRequest.getProductId()));
        product.setQuantity(product.getQuantity() + addProductToWarehouseRequest.getQuantity());

        productRepository.save(product);
    }

    @Override
    public BookedProductsDto checkProductQuantity(CartDto cartDto) {
        Collection<Product> products = productRepository.findAllById(cartDto.getProducts().keySet());
        if (products.size() < cartDto.getProducts().size()) {
            throw new ProductInShoppingCartLowQuantityInWarehouseException();
        }

        float deliveryVolume = 0;
        float deliveryWeight = 0;
        boolean fragile = false;

        for (Product product : products) {
            if (cartDto.getProducts().get(product.getProductId()) > product.getQuantity()) {
                throw new ProductInShoppingCartLowQuantityInWarehouseException();
            }

            deliveryVolume = deliveryVolume + (product.getWidth() * product.getHeight() * product.getDepth());
            deliveryWeight = deliveryWeight + product.getWeight();

            if (product.isFragile()) {
                fragile = true;
            }
        }

        return BookedProductsDto.builder()
                .deliveryVolume(deliveryVolume)
                .deliveryWeight(deliveryWeight)
                .fragile(fragile)
                .build();
    }
}
