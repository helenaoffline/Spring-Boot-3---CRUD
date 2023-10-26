//service aplied to get mapping all products
//service aplied to get products by id
//service aplied to update products by id
package com.example.projects1.controllers;



import java.util.List;
import java.util.UUID;

import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projects1.dtos.ProductRecordDto;
import com.example.projects1.models.ProductModel;
import com.example.projects1.repositories.ProductRepository;
import com.example.projects1.services.ProductService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework. hateoas. server. mvc. WebMvcLinkBuilder. methodOn;


import jakarta.validation.Valid;
//2
@RestController
public class ProductController {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	private ProductService productService;
	
	@PostMapping("/products")
	public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){
		ProductModel savedProduct=productService.saveProduct(productRecordDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
	}
	@GetMapping("/products")
	public ResponseEntity<List<ProductModel>> getAllProducts(){
		List<ProductModel>productList=productService.getAllProducts();
		if (! productList.isEmpty()) {
			for(ProductModel product : productList) {
				UUID id = product.getIdProduct();
				product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(productList);
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Object>getOneProduct(@PathVariable(value="id")UUID id){
		Optional<ProductModel> productO=productService.getOneProduct(id);
		if(productO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productO.get());
		}
		productO.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Product List"));
		return ResponseEntity.status(HttpStatus.OK).body(productO.get());
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Object>updateProduct(@PathVariable(value="id")UUID id,
			@RequestBody @Valid ProductRecordDto productRecordDto){
		Optional<ProductModel> productO=productService.getOneProduct(id);
		if(productO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
		}
		var productModel= productO.get();
		BeanUtils.copyProperties(productRecordDto, productModel);
		return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(productModel));
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Object>deleteProduct(@PathVariable(value="id")UUID id){
		Optional<ProductModel> productO=productRepository.findById(id);
		if(productO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productO.get());
		}
		productService.deleteProduct(id);
		return ResponseEntity.status(HttpStatus.OK).body("PRoduct deleted succesfully");
	}
	
	
}
//service



	