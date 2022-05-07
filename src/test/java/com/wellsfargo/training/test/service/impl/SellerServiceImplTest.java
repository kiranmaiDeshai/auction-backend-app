package com.wellsfargo.training.test.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wellsfargo.training.test.dto.ProductCategory;
import com.wellsfargo.training.test.dto.ProductDetails;
import com.wellsfargo.training.test.dto.ProductDto;
import com.wellsfargo.training.test.entity.Bid;
import com.wellsfargo.training.test.entity.Product;
import com.wellsfargo.training.test.exception.ProductNotFoundException;
import com.wellsfargo.training.test.repository.BidRepository;
import com.wellsfargo.training.test.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class SellerServiceImplTest {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private BidRepository bidRepository;

	@InjectMocks
	private SellerServiceImpl sellerServiceImpl;

	@Test
	void testCreateProduct() {
		doReturn(new Product()).when(productRepository).save(Mockito.any(Product.class));
		assertNotNull(sellerServiceImpl.createProduct(createProductDto()));
	}

	@Test
	void testGetAllProducts() {
		doReturn(Arrays.asList(new Product())).when(productRepository).findAll();
		assertEquals(1, sellerServiceImpl.getAllProducts().size());
	}

	@Test
	void testGetProductDetails() {
		doReturn(Optional.of(new Product())).when(productRepository).findById(Mockito.anyLong());
		doReturn(Arrays.asList(new Bid())).when(bidRepository).findByProductIdOrderByAmountDesc(Mockito.anyLong());
		ProductDetails prodDetails = sellerServiceImpl.getProductDetails(1L);
		assertNotNull(prodDetails);
		assertNotNull(prodDetails.getProduct());
		assertEquals(1, prodDetails.getBids().size());
	}

	@Test
	void testGetProductDetails_whenNoProduct() {
		doReturn(Optional.empty()).when(productRepository).findById(Mockito.anyLong());
		assertThrows(ProductNotFoundException.class, () -> sellerServiceImpl.getProductDetails(1L));
	}

	@Test
	void testGetProductBids() {
		doReturn(Arrays.asList(new Bid())).when(bidRepository).findByProductIdOrderByAmountDesc(Mockito.anyLong());
		assertEquals(1, sellerServiceImpl.getProductBids(1L).size());
	}

	@Test
	void testDeleteProduct() {
		Product product = new Product();
		product.setId(123L);
		doReturn(Optional.of(product)).when(productRepository).findById(Mockito.anyLong());
		doNothing().when(productRepository).deleteById(Mockito.anyLong());
		assertDoesNotThrow(() -> sellerServiceImpl.deleteProduct(1L));
	}

	private ProductDto createProductDto() {
		ProductDto productDto = new ProductDto();
		productDto.setId(1L);
		productDto.setName("zxcva");
		productDto.setFirstName("TestFirst1");
		productDto.setLastName("TestLast1");
		productDto.setAddress("Hyderabad");
		productDto.setCity("Hyderabad");
		productDto.setBidEndDate(LocalDate.now().plusDays(3));
		productDto.setCategory(ProductCategory.ORNAMENT);
		productDto.setDetailedDescription("TestDetailedDesc");
		productDto.setShortDescription("ShortDesc");
		productDto.setState("Telangana");
		productDto.setEmail("test@test.com");
		productDto.setPhone(9878451245L);
		productDto.setPin("500081");
		productDto.setStartPrice(125.5d);
		return productDto;
	}

}
