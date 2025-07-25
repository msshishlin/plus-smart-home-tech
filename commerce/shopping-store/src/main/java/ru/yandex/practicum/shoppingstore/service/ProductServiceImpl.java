package ru.yandex.practicum.shoppingstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductCategory;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductDto;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.ProductState;
import ru.yandex.practicum.interactionapi.dto.shoppingstore.QuantityState;
import ru.yandex.practicum.interactionapi.exception.shoppingstore.ProductNotFoundException;
import ru.yandex.practicum.shoppingstore.model.Product;
import ru.yandex.practicum.shoppingstore.repository.ProductRepository;
import ru.yandex.practicum.shoppingstore.service.mapper.ProductMapper;

import java.util.UUID;

/**
 * Сервис для работы с товарами.
 */
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    /**
     * Хранилище данных для товаров.
     */
    private final ProductRepository productRepository;

    /**
     * Маппер для сущности товаров.
     */
    private final ProductMapper productMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        return productMapper.mapToProductDto(productRepository.save(productMapper.mapToProduct(productDto)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDto findById(UUID productId) throws ProductNotFoundException {
        return productMapper.mapToProductDto(productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDto> findByProductCategory(ProductCategory productCategory, Pageable pageable) {
        return productRepository.findByProductCategory(productCategory, pageable).map(productMapper::mapToProductDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDto updateProduct(ProductDto productDto) throws ProductNotFoundException {
        if (productRepository.findById(productDto.getProductId()).isEmpty()) {
            throw new ProductNotFoundException(productDto.getProductId());
        }

        return productMapper.mapToProductDto(productRepository.save(productMapper.mapToProduct(productDto)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setQuantityState(UUID productId, QuantityState quantityState) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        product.setQuantityState(quantityState);

        return productRepository.save(product).getQuantityState() == quantityState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteProduct(UUID productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        product.setProductState(ProductState.DEACTIVATE);

        return productRepository.save(product).getProductState() == ProductState.DEACTIVATE;
    }
}
