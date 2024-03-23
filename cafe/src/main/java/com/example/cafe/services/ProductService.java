package com.example.cafe.services;

import com.example.cafe.domain.dtos.Product;
import com.example.cafe.domain.entities.CategoryJpa;
import com.example.cafe.domain.entities.ProductJpa;
import com.example.cafe.handlingException.RecordNotComplete;
import com.example.cafe.handlingException.RecordNotFoundException;
import com.example.cafe.mappers.ProductDtoConverter;
import com.example.cafe.repositories.CategoryRepo;
import com.example.cafe.repositories.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepo ProductRepo;
    private final CategoryRepo categoryRepo;
    private final ProductDtoConverter productDtoConverter;
    Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepo ProductRepo,
                          CategoryRepo categoryRepo,
                          ProductDtoConverter productDtoConverter)
    {
        this.ProductRepo = ProductRepo;
        this.categoryRepo = categoryRepo;
        this.productDtoConverter = productDtoConverter;
    }
    public List<Product> getAll()
    {
        List<ProductJpa> productJpas = ProductRepo.findAll();
        List<Product> products = new ArrayList<>();
        for(ProductJpa p : productJpas)
        {
            products.add(productDtoConverter.convert(p));
        }
        return products;
    }

    public Product findById(Long id)
    {
        Optional<ProductJpa> ProductJpa = ProductRepo.findById(id);
        if(ProductJpa.isPresent()) {
            Product product = productDtoConverter.convert(ProductJpa.get());
            return product;
        }else
            throw new RecordNotFoundException("This Record Is Not Found Of Id = "+id);
    }

    public ProductJpa insert(Product product)
    {
        try {
            ProductJpa ProductJpa = productDtoConverter.convert(product);
            CategoryJpa categoryJpa = categoryRepo.findById(product.getCategoryId()).get();
            ProductJpa.setCategoryJpa(categoryJpa);
            ProductJpa productJpaInserted = ProductRepo.save(ProductJpa);
            return productJpaInserted;
        }catch (Exception ex)
        {
            logger.error(ex.getMessage());
            throw new RecordNotComplete("This Record Is Not Inserted");
        }
    }

    public ProductJpa update(Product product)
    {
        try {
            Optional<ProductJpa> ProductJpa = ProductRepo.findById(product.getId());
            Optional<CategoryJpa> categoryJpa = categoryRepo.findById(product.getCategoryId());

            if (ProductJpa.isPresent() && categoryJpa.isPresent()) {
                ProductJpa ProductJpaUpdate = productDtoConverter.convert(product);
                ProductJpaUpdate.setCategoryJpa(categoryJpa.get());
                ProductJpa productJpaUpdated = ProductRepo.save(ProductJpaUpdate);
                return productJpaUpdated;
            }
            else
                throw new RecordNotFoundException("This Record Is Not Found Of Id = "+product.getId());
        }catch (Exception ex)
        {
            logger.error(ex.getMessage());
            throw new RecordNotComplete("This Record Is Not Updated");
        }
    }
    public void delete(Long id)
    {
        ProductRepo.deleteById(id);
    }
}
