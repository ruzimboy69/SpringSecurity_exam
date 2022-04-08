package com.example.springsecurity_exam.controller;

import com.example.springsecurity_exam.dto.ApiResponse;
import com.example.springsecurity_exam.entity.Product;
import com.example.springsecurity_exam.repository.ProductRepository;
import com.example.springsecurity_exam.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
//    @PreAuthorize("hasAuthority('READ_ALL_PRODUCT')")
    @GetMapping
    public HttpEntity<?> getAll(){
        ApiResponse all = productService.getAll();
        return ResponseEntity.status(all.isSuccess() ? HttpStatus.FOUND :HttpStatus.NOT_FOUND).body(all);
    }
    @PreAuthorize("hasAuthority('READ_PRODUCT')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        ApiResponse byId = productService.getById(id);
        return  ResponseEntity.status(byId.isSuccess() ? HttpStatus.FOUND :HttpStatus.NOT_FOUND).body(byId);
    }
    @PreAuthorize("hasAuthority('ADD_PRODUCT')")
    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody Product product){
        ApiResponse add = productService.add(product);
        return ResponseEntity.status(add.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(add);
    }
    @PreAuthorize("hasAuthority('EDIT_PRODUCT')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id,@RequestBody Product product){
        ApiResponse edit = productService.edit(id, product);
        return ResponseEntity.status(edit.isSuccess() ? HttpStatus.ACCEPTED :HttpStatus.CONFLICT).body(edit);
    }
    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse delete = productService.delete(id);
        return ResponseEntity.status(delete.isSuccess() ? HttpStatus.OK:HttpStatus.NOT_FOUND).body(delete);
    }

}
