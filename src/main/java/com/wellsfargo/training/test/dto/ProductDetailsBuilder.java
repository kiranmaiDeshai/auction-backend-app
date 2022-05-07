package com.wellsfargo.training.test.dto;

import java.util.List;

public class ProductDetailsBuilder {

	private ProductDto product;

	private List<BidDto> bids;

	public static ProductDetailsBuilder newInstance() {
		return new ProductDetailsBuilder();
	}

	public ProductDetailsBuilder withProduct(ProductDto product) {
		this.product = product;
		return this;
	}

	public ProductDetailsBuilder withBids(List<BidDto> bids) {
		this.bids = bids;
		return this;
	}

	public ProductDetails build() {
		ProductDetails prod = new ProductDetails();
		prod.setProduct(this.product);
		prod.setBids(this.bids);
		return prod;
	}

}
