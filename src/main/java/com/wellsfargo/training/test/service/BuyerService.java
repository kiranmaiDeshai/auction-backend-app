package com.wellsfargo.training.test.service;

import com.wellsfargo.training.test.dto.BidDto;

public interface BuyerService {
	
	BidDto placeBid(BidDto bidDto);
	
	void updateBid(Long productId, String email, double newBidAmount);
	
}
