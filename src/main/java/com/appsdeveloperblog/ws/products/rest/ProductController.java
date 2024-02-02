package com.appsdeveloperblog.ws.products.rest;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.ws.products.service.ProductServiceImpl;

@RestController
@RequestMapping("/products")
public class ProductController {

  ProductServiceImpl productService;

  public ProductController(ProductServiceImpl productService) {
    this.productService = productService;
  }

  @PostMapping
  public ResponseEntity<Object> createProduct(@RequestBody CreateProductRequestModel product) {
    String productID;
    try {
      productID = productService.createProduct(product);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ErrorMessage(new Date(), e.getMessage(), "/products"));
    }

    return ResponseEntity.status(HttpStatus.OK).body(productID);
  }
}
