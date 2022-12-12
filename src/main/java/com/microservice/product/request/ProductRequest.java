package com.microservice.product.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {

    private long id;
    private String name;
    private long price;
    private long quantity;
}
