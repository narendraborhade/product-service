package com.microservice.product.service;

import com.microservice.product.entity.Product;
import com.microservice.product.exception.ProductServiceCustomException;
import com.microservice.product.repository.ProductRepository;
import com.microservice.product.request.ProductRequest;
import com.microservice.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService{



    private final ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("ProductServiceImpl - addProduct");

        Product product = Product.builder().productName(productRequest.getName()).quantity(
                productRequest.getQuantity()).price(productRequest.getPrice()).build();

         product = productRepository.save(product);

        log.info("ProductServiceImpl - Product Created");
        log.info("ProductServiceImpl - Product Id : " + product.getProductId());
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("ProductServiceImpl - getProductById");
        log.info("ProductServiceImpl - getProductById for productId: {}", productId);

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductServiceCustomException("Product with give Product Id not found", "PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();

        copyProperties(product, productResponse);

        log.info("ProductServiceImpl | getProductById | productResponse :" + productResponse);
        return productResponse;
    }

    @Override
    public void updateProduct(long productId, ProductRequest productRequest) {
        log.info("ProductServiceImpl - updateProduct");

        Product product
                = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException(
                        "Product with given Id not found",
                        "PRODUCT_NOT_FOUND"
                ));

        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);

        log.info("Product Quantity updated Successfully");
    }

    @Override
    public void deleteProductById(long productId) {
        log.info("ProductServiceImpl - deleteProductById");
        log.info("ProductServiceImpl Product id: {}", productId);

        if (!productRepository.existsById(productId)) {
            log.info("Im in this loop {}", !productRepository.existsById(productId));
            throw new ProductServiceCustomException(
                    "Product with given with Id: " + productId + " not found:",
                    "PRODUCT_NOT_FOUND");
        }
        log.info("Deleting Product with id: {}", productId);
        productRepository.deleteById(productId);

    }
}
