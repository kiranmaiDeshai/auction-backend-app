package com.wellsfargo.training.test.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.training.test.dto.BidDto;
import com.wellsfargo.training.test.exception.InvalidDataException;
import com.wellsfargo.training.test.service.BuyerService;

@RestController
@RequestMapping("/e-auction/api/v1/buyer")
@CrossOrigin
public class BuyerController {

	@Autowired
	private BuyerService buyerService;

	@PostMapping("/place-bid")
	public ResponseEntity<BidDto> placeBid(@Valid @RequestBody BidDto bidDto, BindingResult result) {
		if (result.hasErrors()) {
			throw new InvalidDataException("Invalid Data");
		}
		return ResponseEntity.ok(buyerService.placeBid(bidDto));
	}

	@PostMapping("/update-bid/{productId}/{buyerEmailld}/{newBidAmount}")
	public void updateBid(@PathVariable("productId") Long productId, @PathVariable("buyerEmailld") String buyerEmailld,
			@PathVariable("newBidAmount") double newBidAmount) {
		this.buyerService.updateBid(productId, buyerEmailld, newBidAmount);
	}

}
