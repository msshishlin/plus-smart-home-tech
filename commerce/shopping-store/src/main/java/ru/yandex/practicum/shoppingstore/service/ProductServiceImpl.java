package ru.yandex.practicum.shoppingstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.interactionapi.exception.ProductNotFoundException;
import ru.yandex.practicum.shoppingstore.dto.ProductDto;
import ru.yandex.practicum.shoppingstore.mapper.ProductMapper;
import ru.yandex.practicum.shoppingstore.model.Product;
import ru.yandex.practicum.shoppingstore.model.ProductCategory;
import ru.yandex.practicum.shoppingstore.model.ProductState;
import ru.yandex.practicum.shoppingstore.model.QuantityState;
import ru.yandex.practicum.shoppingstore.repository.ProductRepository;

import java.util.UUID;

/**
 * Сервис для работы с товарами.
 */
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    /**
     * Хранилище товаров.
     */
    private final ProductRepository productRepository;

    /**
     * Маппер для сущности товаров.
     */
    private final ProductMapper productMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        return productMapper.mapToProductDto(productRepository.save(productMapper.mapToProduct(productDto)));
    }

    @Override
    public ProductDto findById(UUID productId) {
        return productMapper.mapToProductDto(productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId)));
    }

    @Override
    public Page<ProductDto> findByProductCategory(ProductCategory productCategory, Pageable pageable) {
        return productRepository.findByProductCategory(productCategory, pageable).map(productMapper::mapToProductDto);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        if (productRepository.findById(productDto.getProductId()).isEmpty()) {
            throw new ProductNotFoundException(productDto.getProductId());
        }

        return productMapper.mapToProductDto(productRepository.save(productMapper.mapToProduct(productDto)));
    }

    @Override
    public boolean setQuantityState(UUID productId, QuantityState quantityState) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        product.setQuantityState(quantityState);

        return productRepository.save(product).getQuantityState() == quantityState;
    }

    @Override
    public boolean deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        product.setProductState(ProductState.DEACTIVATE);

        return productRepository.save(product).getProductState() == ProductState.DEACTIVATE;
    }
}
