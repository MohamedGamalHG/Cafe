package com.example.cafe.controllers;

import com.example.cafe.domain.dtos.Category;
import com.example.cafe.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category")
@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/allCategory")
    public ResponseEntity<?> getAll()
    {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody Category category)
    {
        boolean t = categoryService.insert(category);
        return ResponseEntity.ok(t);
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Category category)
    {
        boolean t = categoryService.update(category);
        return ResponseEntity.ok(t);
    }

    @DeleteMapping("/delete/{id}")
    public  boolean delete(@PathVariable Long id)
    {
        categoryService.delete(id);
        return true;
    }
}
