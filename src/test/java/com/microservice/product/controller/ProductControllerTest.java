package com.microservice.product.controller;

import com.microservice.product.request.ProductRequest;
import com.microservice.product.response.ProductResponse;
import com.microservice.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
    ProductRequest productRequest = ProductRequest.builder().id(1).price(100).quantity(10).name("Product1").build();
    ProductResponse productResponse = ProductResponse.builder().productName("Product1").productId(1).price(100).quantity(10).build();
    String productInput = "{\"productId\": 4,\"productName\": \"Product-4\",\"price\": 500,\"quantity\": 150}";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    public void testAddProduct() throws Exception {
        Mockito.when(productService.addProduct(Mockito.any(ProductRequest.class))).thenReturn(Mockito.anyLong());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product")
                .accept(MediaType.APPLICATION_JSON).content(productInput)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void testGetProductById() throws Exception {
        Mockito.when(productService.getProductById(Mockito.anyLong())).thenReturn(productResponse);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/1");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"productId\": 1,\"productName\": \"Product1\",\"price\": 100,\"quantity\": 10}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Mockito.doNothing().when(productService).updateProduct(Mockito.anyLong(), Mockito.any());
        productService.updateProduct(1,productRequest);
    }

    @Test
    public void testDeleteProductById(){
        Mockito.doNothing().when(productService).deleteProductById(1);
        productService.deleteProductById(1);
    }
}
