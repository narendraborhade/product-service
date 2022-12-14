package com.microservice.product.service;

import com.microservice.product.entity.Product;
import com.microservice.product.repository.ProductRepository;
import com.microservice.product.request.ProductRequest;
import com.microservice.product.response.ProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    Product product = Product.builder().productName("Product1").quantity(10).price(100).build();
    ProductRequest productRequest = ProductRequest.builder().price(100).quantity(10).name("Product1").build();

    ProductResponse productResponse = ProductResponse.builder().productName("Product1").productId(0).price(100).quantity(10).build();


    @Test
    public void testAddProduct(){
        Mockito.when(productRepository.save(product)).thenReturn(product);
        long expected = productService.addProduct(productRequest);
        assertEquals(product.getProductId(), expected);
    }

    @Test
    public void testGetProductByIdProduct(){
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        ProductResponse response = productService.getProductById(0);
        assertEquals(productResponse, response);
    }

    @Test
    public void testUpdateProduct(){
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);
        //Mockito.doNothing().when(productService).updateProduct(1L, productRequest);
        productService.updateProduct(1L,productRequest);
    }

    @Test
    public void testDeleteProduct(){
        Mockito.when(productRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.doNothing().when(productRepository).deleteById(1L);
        productService.deleteProductById(1L);
    }


}
