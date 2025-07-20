package ru.yandex.practicum.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.interactionapi.exception.ProductNotFoundException;
import ru.yandex.practicum.warehouse.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.mapper.ProductMapper;
import ru.yandex.practicum.warehouse.model.Product;
import ru.yandex.practicum.warehouse.repository.ProductRepository;

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
}
