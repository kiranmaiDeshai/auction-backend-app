package com.wellsfargo.training.test.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductDetails {

	private ProductDto product;

	private List<BidDto> bids;

}
