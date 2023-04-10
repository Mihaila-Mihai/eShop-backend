package com.javaproject.eshop.helpers;

import com.javaproject.eshop.controller.ProductController;
import com.javaproject.eshop.entity.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductModel> {
    public ProductModelAssembler() {
        super(ProductController.class, ProductModel.class);
    }

    @Override
    public ProductModel toModel(Product entity) {
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(entity, productModel);
        return productModel;
    }
}
