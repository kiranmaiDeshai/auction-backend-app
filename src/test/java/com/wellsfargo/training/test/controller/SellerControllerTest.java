package com.wellsfargo.training.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wellsfargo.training.test.dto.BidDto;
import com.wellsfargo.training.test.dto.ProductCategory;
import com.wellsfargo.training.test.dto.ProductDetails;
import com.wellsfargo.training.test.dto.ProductDto;
import com.wellsfargo.training.test.service.SellerService;

@WebMvcTest(SellerController.class)
@AutoConfigureMockMvc
class SellerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SellerService sellerService;

	private ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void setup() {
		mapper.registerModule(new JavaTimeModule());
	}

	@Test
	void testPlaceBid() throws Exception {
		ProductDto productDto = createProductDto();
		ProductDto savedProductDto = createProductDto();
		savedProductDto.setId(123L);

		when(sellerService.createProduct(productDto)).thenReturn(savedProductDto);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/e-auction/api/v1/seller/add-product")
				.content(this.mapper.writeValueAsString(productDto)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		assertEquals(mapper.writeValueAsString(savedProductDto), result.getResponse().getContentAsString());
	}

	@Test
	void testAddProduct_whenDataIsInvalid() throws Exception {
		ProductDto productDto = createProductDto();
		productDto.setFirstName("A");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/e-auction/api/v1/seller/add-product")
				.content(this.mapper.writeValueAsString(productDto)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(400, result.getResponse().getStatus());
	}

	@Test
	void testAddProduct_whenInvalidBidEndDate() throws Exception {
		ProductDto productDto = createProductDto();
		productDto.setBidEndDate(LocalDate.now().minusDays(2));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/e-auction/api/v1/seller/add-product")
				.content(this.mapper.writeValueAsString(productDto)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(400, result.getResponse().getStatus());
	}

	@Test
	void testGetAllProducts() throws Exception {
		doReturn(Arrays.asList(createProductDto())).when(sellerService).getAllProducts();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/e-auction/api/v1/seller/all-products")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	void testShowBids() throws Exception {
		ProductDetails productDetails = new ProductDetails();
		productDetails.setProduct(createProductDto());
		productDetails.setBids(Arrays.asList(new BidDto()));
		doReturn(productDetails).when(sellerService).getProductDetails(Mockito.anyLong());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/e-auction/api/v1/seller/show-bids/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	void testDeleteProduct() throws Exception {
		doNothing().when(sellerService).deleteProduct(Mockito.anyLong());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/e-auction/api/v1/seller/delete/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}

	private ProductDto createProductDto() {
		ProductDto productDto = new ProductDto();
		productDto.setId(1L);
		productDto.setName("asdfd");
		productDto.setAddress("Hyderabad");
		productDto.setBidEndDate(LocalDate.now().plusDays(3));
		productDto.setCategory(ProductCategory.ORNAMENT);
		productDto.setCity("Hyderabad");
		productDto.setDetailedDescription("TestDetailedDesc");
		productDto.setEmail("test@test.com");
		productDto.setFirstName("TestFirst");
		productDto.setLastName("TestLast");
		productDto.setPhone(9878451245L);
		productDto.setPin("500081");
		productDto.setShortDescription("ShortDesc");
		productDto.setState("Telangana");
		productDto.setStartPrice(125.5d);
		return productDto;
	}

}
