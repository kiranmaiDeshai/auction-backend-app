package com.wellsfargo.training.test.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wellsfargo.training.test.dto.BidDto;
import com.wellsfargo.training.test.dto.ProductCategory;
import com.wellsfargo.training.test.dto.ProductDto;
import com.wellsfargo.training.test.entity.Bid;
import com.wellsfargo.training.test.exception.BidNotFoundException;
import com.wellsfargo.training.test.exception.InvalidBidException;
import com.wellsfargo.training.test.repository.BidRepository;
import com.wellsfargo.training.test.service.SellerService;

@ExtendWith(MockitoExtension.class)
class BuyerServiceImplTest {

	@Mock
	private SellerService sellerService;

	@Mock
	private BidRepository bidRepository;

	@InjectMocks
	private BuyerServiceImpl buyerServiceImpl;

	@Test
	void testPlaceBid() {
		doReturn(createProductDto()).when(sellerService).getProductById(Mockito.anyLong());
		doReturn(new ArrayList<>()).when(bidRepository).findByProductIdAndEmail(Mockito.anyLong(), Mockito.anyString());
		doReturn(new Bid()).when(bidRepository).save(Mockito.any(Bid.class));
		assertNotNull(buyerServiceImpl.placeBid(createBidDto()));
	}

	@Test
	void testPlaceBid_whenInvalidBidDate() {
		ProductDto productDto = createProductDto();
		productDto.setBidEndDate(LocalDate.now().minusDays(2));
		doReturn(productDto).when(sellerService).getProductById(Mockito.anyLong());
		BidDto bidDto = createBidDto();
		assertThrows(InvalidBidException.class, () -> buyerServiceImpl.placeBid(bidDto));		
	}

	@Test
	void testPlaceBid_whenBidsAreNotEmpty() {
		doReturn(createProductDto()).when(sellerService).getProductById(Mockito.anyLong());
		doReturn(Arrays.asList(new Bid())).when(bidRepository).findByProductIdAndEmail(Mockito.anyLong(),
				Mockito.anyString());
		BidDto bidDto = createBidDto();
		assertThrows(InvalidBidException.class, () -> buyerServiceImpl.placeBid(bidDto));
	}

	@Test
	void testUpdateBid() {
		doReturn(Arrays.asList(new Bid())).when(bidRepository).findByProductIdAndEmail(Mockito.anyLong(),
				Mockito.anyString());
		doReturn(new Bid()).when(bidRepository).save(Mockito.any(Bid.class));
		assertDoesNotThrow(() -> buyerServiceImpl.updateBid(1L, "tests@test.com", 123.5d));
	}

	@Test
	void testUpdateBid_whenBidNotFound() {
		doReturn(new ArrayList<>()).when(bidRepository).findByProductIdAndEmail(Mockito.anyLong(), Mockito.anyString());
		assertThrows(BidNotFoundException.class, () -> buyerServiceImpl.updateBid(1L, "tests@test.com", 123.5d));
	}

	private ProductDto createProductDto() {
		ProductDto productDto = new ProductDto();
		productDto.setId(1L);
		productDto.setAddress("Hyd");
		productDto.setCategory(ProductCategory.ORNAMENT);
		productDto.setBidEndDate(LocalDate.now().plusDays(3));
		productDto.setCity("Hyderabad");
		productDto.setDetailedDescription("TestDetailedDesc");
		productDto.setFirstName("TestFirst1");
		productDto.setEmail("test@test1.com");
		productDto.setPin("500081");
		productDto.setLastName("TestLast1");
		productDto.setPhone(9878451245L);
		productDto.setShortDescription("ShortDesc");
		productDto.setState("Telangana");
		productDto.setStartPrice(125.5d);
		return productDto;
	}

	private BidDto createBidDto() {
		BidDto bidDto = new BidDto();
		bidDto.setId(1L);
		bidDto.setFirstName("TestFirstName");
		bidDto.setLastName("TestLastName");
		bidDto.setCity("Hyderabad");
		bidDto.setAddress("Hyderabad");
		bidDto.setAmount(1235.5d);
		bidDto.setEmail("test@testcom");
		bidDto.setId(1L);
		bidDto.setPhone(9876543210L);
		bidDto.setProductId(1L);
		bidDto.setState("Telangana");
		return bidDto;
	}

}
