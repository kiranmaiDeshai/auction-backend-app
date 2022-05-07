package com.wellsfargo.training.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellsfargo.training.test.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
	
	List<Bid> findByProductIdOrderByAmountDesc(Long productId);
	
	List<Bid> findByProductIdAndEmail(Long productId, String email);

}
