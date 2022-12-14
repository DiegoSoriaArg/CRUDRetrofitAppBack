package com.crudandroid.demo.controller;

import com.crudandroid.demo.dto.ProductDTO;
import com.crudandroid.demo.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {

    private List<Product> products = new ArrayList<>(Arrays.asList(
            new Product(1, "producto1", 100),
            new Product(2, "producto2", 200),
            new Product(3, "producto3", 300),
            new Product(4, "producto5", 400)
    ));

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getOne(@PathVariable("id") int id) {
        Product producto = findById(id);
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDTO dto) {
        int index = products.isEmpty()? 1 : getLastIndex() + 1;
        Product producto = Product.builder().id(index).name(dto.getName()).price(dto.getPrice()).build();
        products.add(producto);
        return ResponseEntity.ok(producto);
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> update(@PathVariable("id") int id, @RequestBody ProductDTO dto) {
        Product producto = findById(id);
        producto.setName(dto.getName());
        producto.setPrice(dto.getPrice());
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Product> delete(@PathVariable("id") int id) {
        Product producto = findById(id);
        products.remove(producto);
        return ResponseEntity.ok(producto);
    }

    private int getLastIndex(){
        return products.stream().max(Comparator.comparing(Product::getId)).get().getId();
    }

    private Product findById(int id) {
        return products.stream().filter(p -> p.getId() == id).findAny().orElse(null);
    }

}
