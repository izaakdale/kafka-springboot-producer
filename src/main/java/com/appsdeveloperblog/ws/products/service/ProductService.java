package com.appsdeveloperblog.ws.products.service;

import com.appsdeveloperblog.ws.products.rest.CreateProductRequestModel;

public interface ProductService {
  String createProduct(CreateProductRequestModel productRestModel) throws Exception;
}
