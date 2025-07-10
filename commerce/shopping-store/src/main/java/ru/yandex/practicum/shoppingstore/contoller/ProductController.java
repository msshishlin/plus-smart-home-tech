package ru.yandex.practicum.shoppingstore.contoller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shoppingstore.dto.ProductDto;
import ru.yandex.practicum.shoppingstore.model.ProductCategory;
import ru.yandex.practicum.shoppingstore.model.QuantityState;
import ru.yandex.practicum.shoppingstore.service.ProductService;

import java.util.UUID;

@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ProductController {
    /**
     * Сервис для работы с товарами.
     */
    private final ProductService productService;

    /**
     * Создание нового товара.
     *
     * @param productDto товар, продаваемый в интернет-магазине.
     * @return товар, продаваемый в интернет-магазине.
     */
    @PutMapping
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto) {
        log.info("Create new product request {}", productDto);
        return productService.createProduct(productDto);
    }

    /**
     * Получить сведения по товару из БД.
     *
     * @param productId идентификатор товара.
     * @return товар, продаваемый в интернет-магазине.
     */
    @GetMapping("/{productId}")
    public ProductDto findById(@PathVariable UUID productId) {
        log.info("Find product with id={} request", productId);
        return productService.findById(productId);
    }

    /**
     * Найти все товары, относящиеся к определенной категории.
     *
     * @param productCategory категория товара.
     * @param pageable        параметры пагинации.
     * @return коллекция товаров.
     */
    @GetMapping
    public Page<ProductDto> findByProductCategory(@RequestParam(name = "category") ProductCategory productCategory, @PageableDefault Pageable pageable) {
        log.info("Find product with category={} and pagination params {} request", productCategory, pageable);
        return productService.findByProductCategory(productCategory, pageable);
    }

    /**
     * Обновление товара.
     *
     * @param productDto товар, продаваемый в интернет-магазине.
     * @return товар, продаваемый в интернет-магазине.
     */
    @PostMapping
    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto) {
        log.info("Update product request {}", productDto);
        return productService.updateProduct(productDto);
    }

    /**
     * Изменить статус остатка товара.
     *
     * @param productId     идентификатор товара.
     * @param quantityState новый статус остатка товара.
     * @return признак успешности обновления статуса остатка товара.
     */
    @PostMapping("/quantityState")
    public boolean setQuantityState(@RequestParam UUID productId, @RequestParam QuantityState quantityState) {
        log.info("Set quantity state {} for product with id={}", quantityState, productId);
        return productService.setQuantityState(productId, quantityState);
    }

    /**
     * Удаление товара.
     *
     * @param productId товар, продаваемый в интернет-магазине.
     * @return признак успешности удаления товара.
     */
    @PostMapping("/removeProductFromStore")
    public boolean removeProduct(@RequestBody @Valid UUID productId) {
        log.info("Delete product with id={}", productId);
        return productService.deleteProduct(productId);
    }
}
