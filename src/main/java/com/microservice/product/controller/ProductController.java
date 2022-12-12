package com.microservice.product.controller;

import com.microservice.product.request.ProductRequest;
import com.microservice.product.response.ProductResponse;
import com.microservice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;

    @RequestMapping("/hello")
    public String message(){
        return "Hello";
    }

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {

        log.info("ProductController - addProduct");
        log.info("ProductController - addProduct - productRequest : " + productRequest.toString());

        long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId) {

        log.info("ProductController - getProductById");
        log.info("ProductController - getProductById - productId : " + productId);

        ProductResponse productResponse
                = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponse> UpdateProduct(@PathVariable("id") long productId, @RequestBody ProductRequest productRequest) {

        log.info("ProductController - UpdateProduct");
        log.info("ProductController - UpdateProduct - productId : " + productId);

        productService.updateProduct(productId, productRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable("id") long productId) {
        productService.deleteProductById(productId);
    }
}
