package com.wellsfargo.training.test.service;

import java.util.List;

import com.wellsfargo.training.test.dto.BidDto;
import com.wellsfargo.training.test.dto.ProductDetails;
import com.wellsfargo.training.test.dto.ProductDto;

public interface SellerService {

	ProductDto createProduct(ProductDto productDto);

	List<ProductDto> getAllProducts();

	ProductDetails getProductDetails(Long productId);

	void deleteProduct(Long productId);

	ProductDto getProductById(Long productId);

	List<BidDto> getProductBids(Long productId);

}
