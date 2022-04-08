package com.example.springsecurity_exam.service;

import com.example.springsecurity_exam.dto.ApiResponse;
import com.example.springsecurity_exam.entity.Product;
import com.example.springsecurity_exam.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public ApiResponse getAll(){
        List<Product> all = productRepository.findAll();
        if(all.isEmpty()){
            return new ApiResponse("not found",false);
        }
        return new ApiResponse("found",true,all);
    }

    public ApiResponse getById(Integer id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()){
            return new ApiResponse("Not found",false);
        }
        Product product = byId.get();
        return new ApiResponse("Found",true,product);


    }

    public ApiResponse add(Product product) {
        Optional<Product> byName = productRepository.findByName(product.getName());
        if(byName.isPresent()){
            return new ApiResponse("Already exist",false);
        }
        Product save = productRepository.save(product);

        return new ApiResponse("Added",true,save);

    }

    public ApiResponse edit(Integer id,Product product) {
        Optional<Product> byId = productRepository.findById(id);
        if(!byId.isPresent()){
            return new ApiResponse("Not found",false);
        }
        Product product1 = byId.get();
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        Product save = productRepository.save(product1);
        return new ApiResponse("Edited",true,save);
    }

    public ApiResponse delete(Integer id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()){
            return new ApiResponse("Not found",false);
        }
        productRepository.deleteById(id);
        return new ApiResponse("Deleted",true);

    }
}
