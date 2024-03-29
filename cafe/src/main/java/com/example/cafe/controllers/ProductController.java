package com.example.cafe.controllers;

import com.example.cafe.domain.dtos.Product;
import com.example.cafe.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product")
@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/allProduct")
    public ResponseEntity<?> getAll()
    {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody Product product)
    {
        return ResponseEntity.ok(productService.insert(product));
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Product product)
    {
        return ResponseEntity.ok(productService.update(product));
    }

    @DeleteMapping("/delete/{id}")
    public  boolean delete(@PathVariable Long id)
    {
        productService.delete(id);
        return true;
    }
}
