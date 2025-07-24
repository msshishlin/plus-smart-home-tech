package ru.yandex.practicum.delivery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.delivery.model.Address;
import ru.yandex.practicum.delivery.model.Delivery;
import ru.yandex.practicum.delivery.repository.AddressRepository;
import ru.yandex.practicum.delivery.repository.DeliveryRepository;
import ru.yandex.practicum.delivery.service.mapper.DeliveryMapper;
import ru.yandex.practicum.interactionapi.dto.AddressDto;
import ru.yandex.practicum.interactionapi.dto.delivery.DeliveryDto;
import ru.yandex.practicum.interactionapi.dto.delivery.DeliveryState;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.ShippedToDeliveryRequest;
import ru.yandex.practicum.interactionapi.exception.delivery.NoDeliveryFoundException;
import ru.yandex.practicum.interactionapi.feign.OrderClient;
import ru.yandex.practicum.interactionapi.feign.WarehouseClient;

import java.util.UUID;

/**
 * Сервис для работы с доставками.
 */
@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {
    /**
     * Хранилище адресов.
     */
    private final AddressRepository addressRepository;

    /**
     * Хранилище данных о доставках.
     */
    private final DeliveryRepository deliveryRepository;

    /**
     * Маппер для сущности доставки.
     */
    private final DeliveryMapper deliveryMapper;

    /**
     * Клиент для сервиса управления заказами.
     */
    private final OrderClient orderClient;

    /**
     * Клиента для сервиса склада товаров.
     */
    private final WarehouseClient warehouseClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = deliveryMapper.mapToDelivery(deliveryDto);

        delivery.setFromAddress(getOrCreateAddress(deliveryDto.getFromAddress()));
        delivery.setToAddress(getOrCreateAddress(deliveryDto.getToAddress()));
        delivery.setState(DeliveryState.CREATED);

        return deliveryMapper.mapToDeliveryDto(deliveryRepository.save(delivery));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateDeliveryCost(OrderDto orderDto) throws NoDeliveryFoundException {
        Delivery delivery = deliveryRepository.findById(orderDto.getDeliveryId()).orElseThrow(() -> new NoDeliveryFoundException(orderDto.getDeliveryId()));

        // Определим, что базовая стоимость равна 5.0.
        double deliveryCost = 5;

        // Умножаем базовую стоимость на число, зависящее от адреса склада,
        // Если адрес склада содержит название ADDRESS_1, то умножаем на 1,
        // Если адрес склада содержит название ADDRESS_2, то умножаем на 2,
        // Складываем получившийся результат с базовой стоимостью.
        deliveryCost = deliveryCost + deliveryCost * ((delivery.getFromAddress().getCountry().equalsIgnoreCase("ADDRESS_1") ? 1 : 2));

        // Если в заказе есть признак хрупкости, умножаем сумму на 0.2 и складываем с полученным на предыдущем шаге итогом.
        if (orderDto.getFragile()) {
            deliveryCost = deliveryCost + deliveryCost * 0.2;
        }

        // Добавляем к сумме, полученной на предыдущих шагах, вес заказа, умноженный на 0.3.
        deliveryCost = deliveryCost + orderDto.getDeliveryWeight() * 0.3;

        // Складываем с полученным на прошлом шаге итогом объём, умноженный на 0.2.
        deliveryCost = deliveryCost + orderDto.getDeliveryVolume() * 0.2;

        // Если нужно доставить на ту же улицу, где находится склад (улица доставки совпадает с адресом склада), то стоимость доставки не увеличивается.
        // Иначе её нужно умножить на 0.2 и сложить с полученным на предыдущем шаге итогом.
        if (!delivery.getFromAddress().getStreet().equalsIgnoreCase(delivery.getToAddress().getStreet())) {
            deliveryCost = deliveryCost + deliveryCost * 0.2;
        }

        return deliveryCost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void productsPickedToDelivery(UUID deliveryId) throws NoDeliveryFoundException {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new NoDeliveryFoundException(deliveryId));
        delivery.setState(DeliveryState.IN_PROGRESS);

        orderClient.handleSuccessfulOrderAssembly(delivery.getOrderId());

        warehouseClient.shippedToDelivery(new ShippedToDeliveryRequest(delivery.getOrderId(), deliveryId));
        deliveryRepository.save(delivery);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulDelivery(UUID deliveryId) throws NoDeliveryFoundException {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new NoDeliveryFoundException(deliveryId));
        delivery.setState(DeliveryState.DELIVERED);

        orderClient.handleSuccessfulDelivery(delivery.getOrderId());
        deliveryRepository.save(delivery);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailureDelivery(UUID deliveryId) throws NoDeliveryFoundException {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new NoDeliveryFoundException(deliveryId));
        delivery.setState(DeliveryState.FAILED);

        orderClient.handleFailureDelivery(delivery.getOrderId());
        deliveryRepository.save(delivery);
    }

    /**
     * Получить или создать адрес в БД.
     *
     * @param addressDto адрес.
     * @return адрес.
     */
    private Address getOrCreateAddress(AddressDto addressDto) {
        return addressRepository
                .findByCountryAndCityAndStreetAndHouseAndFlat(addressDto.getCountry(), addressDto.getCity(), addressDto.getStreet(), addressDto.getHouse(), addressDto.getFlat())
                .orElseGet(() -> addressRepository.save(Address.builder()
                        .country(addressDto.getCountry())
                        .city(addressDto.getCity())
                        .street(addressDto.getStreet())
                        .house(addressDto.getHouse())
                        .flat(addressDto.getFlat())
                        .build()));
    }
}
