package com.microservice.product.service;

import com.microservice.product.request.ProductRequest;
import com.microservice.product.response.ProductResponse;

public interface ProductService {

    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void updateProduct(long productId, ProductRequest productRequest);

    void deleteProductById(long productId);
}
