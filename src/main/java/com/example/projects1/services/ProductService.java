package com.example.projects1.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.projects1.controllers.ProductController;
import com.example.projects1.dtos.ProductRecordDto;
import com.example.projects1.models.ProductModel;
import com.example.projects1.repositories.ProductRepository;

import jakarta.validation.Valid;

@Service
public class ProductService {
@Autowired
private ProductRepository productRepository;

//put
public ProductModel saveProduct(ProductRecordDto productRecordDto){
	ProductModel productModel = new ProductModel();
	BeanUtils.copyProperties(productRecordDto, productModel);
	return productRepository.save(productModel);
}

//getall
public List<ProductModel> getAllProducts(){
	List<ProductModel>productList=productRepository.findAll();
	return productList;
}

//getbyid
public Optional<ProductModel> getOneProduct(UUID id){
	return productRepository.findById(id);
}
//update
public ProductModel updateProduct(ProductModel productModel){
	return productRepository.save(productModel);
}
//delte
public void deleteProduct(UUID id){
	productRepository.deleteById(id);
}


}

