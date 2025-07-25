package ru.yandex.practicum.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.interactionapi.dto.shoppingcart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.*;
import ru.yandex.practicum.interactionapi.exception.warehouse.*;
import ru.yandex.practicum.warehouse.model.OrderBooking;
import ru.yandex.practicum.warehouse.model.Product;
import ru.yandex.practicum.warehouse.repository.OrderBookingRepository;
import ru.yandex.practicum.warehouse.repository.ProductRepository;
import ru.yandex.practicum.warehouse.service.mapper.ProductMapper;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Сервис для работы с товарами на складе.
 */
@RequiredArgsConstructor
@Service
public class WarehouseServiceImpl implements WarehouseService {
    /**
     * Хранилище данных о забронированных для заказа товарах.
     */
    private final OrderBookingRepository orderBookingRepository;

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
    public BookedProductsDto checkProductQuantity(ShoppingCartDto shoppingCartDto) throws ProductInShoppingCartNotInWarehouse {
        Collection<Product> products = productRepository.findAllById(shoppingCartDto.getProducts().keySet());
        if (products.size() < shoppingCartDto.getProducts().size()) {
            throw new ProductInShoppingCartNotInWarehouse();
        }

        float deliveryVolume = 0;
        float deliveryWeight = 0;
        boolean fragile = false;

        for (Product product : products) {
            if (shoppingCartDto.getProducts().get(product.getProductId()) > product.getQuantity()) {
                throw new ProductInShoppingCartNotInWarehouse();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public BookedProductsDto assemblyProductsForOrder(AssemblyProductsForOrderRequest assemblyProductsForOrderRequest) throws ProductInShoppingCartLowQuantityInWarehouseException {
        Collection<Product> products = productRepository.findAllById(assemblyProductsForOrderRequest.getProducts().keySet());

        float deliveryVolume = 0;
        float deliveryWeight = 0;
        boolean fragile = false;

        for (Product product : products) {
            if (assemblyProductsForOrderRequest.getProducts().get(product.getProductId()) > product.getQuantity()) {
                throw new ProductInShoppingCartNotInWarehouse();
            }

            product.setQuantity(product.getQuantity() - assemblyProductsForOrderRequest.getProducts().get(product.getProductId()));

            deliveryVolume = deliveryVolume + (product.getWidth() * product.getHeight() * product.getDepth());
            deliveryWeight = deliveryWeight + product.getWeight();

            if (product.isFragile()) {
                fragile = true;
            }
        }
        productRepository.saveAll(products);

        OrderBooking orderBooking = OrderBooking.builder()
                .orderId(assemblyProductsForOrderRequest.getOrderId())
                .deliveryVolume(deliveryVolume)
                .deliveryWeight(deliveryWeight)
                .fragile(fragile)
                .products(assemblyProductsForOrderRequest.getProducts())
                .build();
        orderBookingRepository.save(orderBooking);

        return BookedProductsDto.builder()
                .deliveryVolume(deliveryVolume)
                .deliveryWeight(deliveryWeight)
                .fragile(fragile)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shippedToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest) throws NoOrderBookingFoundException {
        OrderBooking orderBooking = orderBookingRepository.findById(shippedToDeliveryRequest.getOrderId()).orElseThrow(() -> new NoOrderBookingFoundException(shippedToDeliveryRequest.getOrderId()));
        orderBooking.setDeliveryId(shippedToDeliveryRequest.getDeliveryId());

        orderBookingRepository.save(orderBooking);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void acceptReturnOfProducts(Map<UUID, Integer> products) {
        Collection<Product> productsFromDB = productRepository.findAllById(products.keySet());

        for (Product product : productsFromDB) {
            product.setQuantity(product.getQuantity() + products.get(product.getProductId()));
        }

        productRepository.saveAll(productsFromDB);
    }
}
