package ru.yandex.practicum.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.interactionapi.exception.warehouse.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.interactionapi.exception.warehouse.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.interactionapi.exception.warehouse.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.warehouse.model.Product;
import ru.yandex.practicum.warehouse.repository.ProductRepository;
import ru.yandex.practicum.warehouse.service.mapper.ProductMapper;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void createNewProductInWarehouse(NewProductInWarehouseRequest newProductInWarehouseRequest) throws SpecifiedProductAlreadyInWarehouseException {
        Product product = productMapper.mapToProduct(newProductInWarehouseRequest);
        if (productRepository.findByWidthAndHeightAndDepthAndWeightAndFragile(product.getWidth(), product.getHeight(), product.getDepth(), product.getWeight(), product.isFragile()).isPresent()) {
            throw new SpecifiedProductAlreadyInWarehouseException();
        }

        productRepository.save(product);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest addProductToWarehouseRequest) throws NoSpecifiedProductInWarehouseException {
        Product product = productRepository.findById(addProductToWarehouseRequest.getProductId()).orElseThrow(NoSpecifiedProductInWarehouseException::new);
        product.setQuantity(product.getQuantity() + addProductToWarehouseRequest.getQuantity());

        productRepository.save(product);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookedProductsDto checkProductQuantity(ShoppingCartDto shoppingCartDto) throws ProductInShoppingCartLowQuantityInWarehouseException {
        Collection<Product> products = productRepository.findAllById(shoppingCartDto.getProducts().keySet());
        if (products.size() < shoppingCartDto.getProducts().size()) {
            throw new ProductInShoppingCartLowQuantityInWarehouseException();
        }

        float deliveryVolume = 0;
        float deliveryWeight = 0;
        boolean fragile = false;

        for (Product product : products) {
            if (shoppingCartDto.getProducts().get(product.getProductId()) > product.getQuantity()) {
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
