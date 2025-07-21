package ru.yandex.practicum.shoppingstore.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductCategory;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductDto;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.QuantityState;
import ru.yandex.practicum.interactionapi.feign.ShoppingStoreClient;
import ru.yandex.practicum.shoppingstore.service.ProductService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ShoppingStoreController implements ShoppingStoreClient {
    /**
     * Сервис для работы с товарами.
     */
    private final ProductService productService;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Create new product request {}", productDto);
        return productService.createProduct(productDto);
    }

    @Override
    public ProductDto findById(UUID productId) {
        log.info("Find product with id={} request", productId);
        return productService.findById(productId);
    }

    @Override
    public Page<ProductDto> findByProductCategory(ProductCategory productCategory, Pageable pageable) {
        log.info("Find product with category={} and pagination params {} request", productCategory, pageable);
        return productService.findByProductCategory(productCategory, pageable);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        log.info("Update product request {}", productDto);
        return productService.updateProduct(productDto);
    }

    @Override
    public boolean setQuantityState(UUID productId, QuantityState quantityState) {
        log.info("Set quantity state {} for product with id={}", quantityState, productId);
        return productService.setQuantityState(productId, quantityState);
    }

    @Override
    public boolean removeProduct(UUID productId) {
        log.info("Delete product with id={}", productId);
        return productService.deleteProduct(productId);
    }
}
