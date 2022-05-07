package com.wellsfargo.training.test.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.wellsfargo.training.test.dto.BidDto;
import com.wellsfargo.training.test.dto.ProductDto;
import com.wellsfargo.training.test.entity.Bid;
import com.wellsfargo.training.test.exception.BidNotFoundException;
import com.wellsfargo.training.test.exception.InvalidBidException;
import com.wellsfargo.training.test.repository.BidRepository;
import com.wellsfargo.training.test.service.BuyerService;
import com.wellsfargo.training.test.service.SellerService;

@Service
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private BidRepository bidRepository;

	@Override
	public BidDto placeBid(BidDto bidDto) {
		// Bid date validation
		ProductDto productDto = this.sellerService.getProductById(bidDto.getProductId());
		if (LocalDate.now().isAfter(productDto.getBidEndDate())) {
			throw new InvalidBidException("Bid already closed");
		}
		// Existing bid validation
		List<Bid> bids = this.bidRepository.findByProductIdAndEmail(bidDto.getProductId(), bidDto.getEmail());
		if (!CollectionUtils.isEmpty(bids)) {
			throw new InvalidBidException("Already bid is placed by this user");
		}
		// Placing bid
		Bid bid = new Bid();
		BeanUtils.copyProperties(bidDto, bid);
		Bid dbBid = bidRepository.save(bid);
		bidDto.setId(dbBid.getId());
		return bidDto;
	}

	@Override
	public void updateBid(Long productId, String email, double newBidAmount) {
		List<Bid> bids = this.bidRepository.findByProductIdAndEmail(productId, email);
		if (!CollectionUtils.isEmpty(bids)) {
			Bid existingBid = bids.get(0);
			existingBid.setAmount(newBidAmount);
			bidRepository.save(existingBid);
		} else {
			throw new BidNotFoundException(
					String.format("No bid exists with email: %s and product: %d", email, productId));
		}
	}

}
