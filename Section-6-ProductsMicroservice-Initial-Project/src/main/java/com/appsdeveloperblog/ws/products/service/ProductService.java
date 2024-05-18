package com.appsdeveloperblog.ws.products.service;

import com.appsdeveloperblog.ws.products.model.CreateProductRestModel;

public interface ProductService {

    String createProduct(CreateProductRestModel restModel) throws  Exception;

}
