package ru.yandex.practicum.shoppingstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductCategory;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductDto;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.QuantityState;
import ru.yandex.practicum.interactionapi.exception.shoppingstore.ProductNotFoundException;
import ru.yandex.practicum.interactionapi.feign.ShoppingStoreClient;
import ru.yandex.practicum.shoppingstore.service.ProductService;

import java.util.UUID;

/**
 * Контроллер для работы с товарами.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class ShoppingStoreController implements ShoppingStoreClient {
    /**
     * Сервис для работы с товарами.
     */
    private final ProductService productService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Creating new product - {}", productDto);
        return productService.createProduct(productDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDto findById(UUID productId) throws ProductNotFoundException {
        log.info("Find product with id={}", productId);
        return productService.findById(productId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDto> findByProductCategory(ProductCategory productCategory, Pageable pageable) {
        log.info("Find product with category={} and pagination params {}", productCategory, pageable);
        return productService.findByProductCategory(productCategory, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDto updateProduct(ProductDto productDto) throws ProductNotFoundException {
        log.info("Update product {}", productDto);
        return productService.updateProduct(productDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setQuantityState(UUID productId, QuantityState quantityState) throws ProductNotFoundException {
        log.info("Set quantity state {} for product with id={}", quantityState, productId);
        return productService.setQuantityState(productId, quantityState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeProduct(UUID productId) throws ProductNotFoundException {
        log.info("Delete product with id={}", productId);
        return productService.deleteProduct(productId);
    }
}
