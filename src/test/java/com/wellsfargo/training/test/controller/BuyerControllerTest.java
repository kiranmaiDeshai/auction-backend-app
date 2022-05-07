package com.wellsfargo.training.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
import com.wellsfargo.training.test.dto.BidDto;
import com.wellsfargo.training.test.service.BuyerService;

@WebMvcTest(BuyerController.class)
@AutoConfigureMockMvc
class BuyerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BuyerService buyerService;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void testAddProduct() throws Exception {
		BidDto bidDto = createBidDto();
		System.out.println(mapper.writeValueAsString(bidDto));
		BidDto savedBidDto = createBidDto();
		savedBidDto.setId(123L);

		when(buyerService.placeBid(bidDto)).thenReturn(savedBidDto);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/e-auction/api/v1/buyer/place-bid")
				.content(this.mapper.writeValueAsString(bidDto)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		assertEquals(mapper.writeValueAsString(savedBidDto), result.getResponse().getContentAsString());
	}

	@Test
	void testAddProduct_whenDataIsInvalid() throws Exception {
		BidDto bidDto = createBidDto();
		bidDto.setPhone(123L);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/e-auction/api/v1/buyer/place-bid")
				.content(this.mapper.writeValueAsString(bidDto)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(400, result.getResponse().getStatus());
	}

	@Test
	void testUpdateBid() throws Exception {
		doNothing().when(buyerService).updateBid(Mockito.anyLong(), Mockito.anyString(), Mockito.anyDouble());
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/e-auction/api/v1/buyer/update-bid/1/test@test.com/123.5").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}

	private BidDto createBidDto() {
		BidDto bidDto = new BidDto();
		bidDto.setAddress("Hyderabad");
		bidDto.setAmount(1235.5d);
		bidDto.setCity("Hyderabad");
		bidDto.setEmail("test@testcom");
		bidDto.setFirstName("TestFirstName");
		bidDto.setId(1L);
		bidDto.setLastName("TestLastName");
		bidDto.setPhone(9876543210L);
		bidDto.setProductId(1L);
		bidDto.setState("Telangana");
		return bidDto;
	}

}
