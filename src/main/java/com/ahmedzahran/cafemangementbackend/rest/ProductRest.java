package com.ahmedzahran.cafemangementbackend.rest;

import com.ahmedzahran.cafemangementbackend.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path="/product")
public interface ProductRest {

    @PostMapping("/add")
    ResponseEntity<String> addNewProduct(@RequestBody Map<String,String> requestMap);

    @GetMapping("/getAllProducts")
    ResponseEntity<List<ProductWrapper>> getAllProducts();

    @PutMapping(path="/update")
    ResponseEntity<String> updateProduct(@RequestBody Map<String,String> requestMap);

    @DeleteMapping(path="/delete/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id);

    @PutMapping(path="/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String,String> requestMap);

}
