package com.wellsfargo.training.test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsfargo.training.test.dto.BidDto;
import com.wellsfargo.training.test.dto.ProductDetails;
import com.wellsfargo.training.test.dto.ProductDetailsBuilder;
import com.wellsfargo.training.test.dto.ProductDto;
import com.wellsfargo.training.test.entity.Bid;
import com.wellsfargo.training.test.entity.Product;
import com.wellsfargo.training.test.exception.ProductNotFoundException;
import com.wellsfargo.training.test.repository.BidRepository;
import com.wellsfargo.training.test.repository.ProductRepository;
import com.wellsfargo.training.test.service.SellerService;

@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private BidRepository bidRepository;

	@Override
	public ProductDto createProduct(ProductDto productDto) {
		Product product = new Product();
		BeanUtils.copyProperties(productDto, product);
		Product dbProduct = productRepository.save(product);
		productDto.setId(dbProduct.getId());
		return productDto;
	}

	@Override
	public List<ProductDto> getAllProducts() {
		List<Product> products = this.productRepository.findAll();
		List<ProductDto> dtos = new ArrayList<>();
		for (Product entity : products) {
			ProductDto dto = new ProductDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public ProductDetails getProductDetails(Long productId) {
		return ProductDetailsBuilder.newInstance().withProduct(getProductById(productId))
				.withBids(getProductBids(productId)).build();
	}

	@Override
	public void deleteProduct(Long productId) {
		ProductDto productDto = getProductById(productId);
		this.productRepository.deleteById(productDto.getId());
	}

	@Override
	public ProductDto getProductById(Long productId) {
		Optional<Product> entity = productRepository.findById(productId);
		if (entity.isPresent()) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(entity.get(), productDto);
			return productDto;
		} else {
			throw new ProductNotFoundException("PRoduct with id " + productId + " not found");
		}
	}

	@Override
	public List<BidDto> getProductBids(Long productId) {
		List<Bid> bids = this.bidRepository.findByProductIdOrderByAmountDesc(productId);
		List<BidDto> dtos = new ArrayList<>();
		for (Bid bid : bids) {
			BidDto dto = new BidDto();
			BeanUtils.copyProperties(bid, dto);
			dtos.add(dto);
		}
		return dtos;
	}

}
