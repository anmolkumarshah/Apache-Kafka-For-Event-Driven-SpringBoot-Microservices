package com.appsdeveloperblog.ws.products.controller;

import com.appsdeveloperblog.ws.products.model.CreateProductRestModel;
import com.appsdeveloperblog.ws.products.model.ErrorMessage;
import com.appsdeveloperblog.ws.products.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody CreateProductRestModel restModel){
        try {
            String productId = productService.createProduct(restModel);
            return  ResponseEntity.status(HttpStatus.CREATED).body(productId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            ErrorMessage msg = new ErrorMessage(new Date(),e.getMessage(),"/products");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
        }
    }

}
