package com.wellsfargo.training.test.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.training.test.dto.ProductDetails;
import com.wellsfargo.training.test.dto.ProductDto;
import com.wellsfargo.training.test.exception.InvalidDataException;
import com.wellsfargo.training.test.service.SellerService;

@RestController
@RequestMapping("/e-auction/api/v1/seller")
@CrossOrigin
public class SellerController {

	@Autowired
	private SellerService sellerService;

	@PostMapping("/add-product")
	public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto, BindingResult result) {
		if (result.hasErrors()) {
			throw new InvalidDataException("Invalid Data");
		}
		return ResponseEntity.ok(this.sellerService.createProduct(productDto));
	}

	@GetMapping("/all-products")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		return ResponseEntity.ok(this.sellerService.getAllProducts());
	}

	@GetMapping("/show-bids/{productId}")
	public ResponseEntity<ProductDetails> showBids(@PathVariable("productId") Long productId) {
		return ResponseEntity.ok(this.sellerService.getProductDetails(productId));
	}

	@DeleteMapping("/delete/{productId}")
	public void deleteProduct(@PathVariable("productId") Long productId) {
		this.sellerService.deleteProduct(productId);
	}

}
