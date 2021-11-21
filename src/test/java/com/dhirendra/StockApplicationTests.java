package com.dhirendra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.dhirendra.entity.Stock;
import com.dhirendra.model.StockDTO;
import com.dhirendra.repository.StockRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class StockApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private StockRepository stockRepository;

	@Test
	void testGetAllStocks() throws Exception {
		mockMvc.perform(get("/api/stocks")).andExpect(status().isOk())
				.andExpect(content().json("["
						+ "{\"name\":\"TCS\",\"currentPrice\":3000.0,\"lastUpdate\":\"2020-12-31T22:59:59.000+0000\"},"
						+ "{\"name\":\"HCL\",\"currentPrice\":1000.0,\"lastUpdate\":\"2012-12-31T20:59:59.000+0000\"}"
						+ "]", true));
	}

	@Test
	void testGetStockById() throws Exception {
		mockMvc.perform(get("/api/stocks/1")).andExpect(status().isOk()).andExpect(content().json(
				"{\"name\":\"TCS\",\"currentPrice\":3000.0,\"lastUpdate\":\"2020-12-31T22:59:59.000+0000\"}", true));
	}

	@Test
	void testPostNewStock() throws Exception {
		StockDTO newStockRequest = new StockDTO();
		newStockRequest.setName("Capgemini");
		newStockRequest.setCurrentPrice(5000d);
		mockMvc.perform(post("/api/stocks").contentType("application/json")
				.content(new ObjectMapper().writeValueAsString(newStockRequest))).andExpect(status().isCreated());

		Optional<Stock> expectedStock = Optional.ofNullable(stockRepository.findStockByName("Capgemini"));
		assertThat(expectedStock).isPresent()
				.hasValueSatisfying(s -> Assertions.assertAll(() -> assertThat(s.getId()).isNotNull(),
						() -> assertThat(s.getName()).isEqualTo("Capgemini"),
						() -> assertThat(s.getCurrentPrice()).isEqualTo(5000d)

				));
	}

	@Test
	void testPutUpdatedPrice() throws Exception {
		double updatedPrice = 1000d;

		Stock expectedStock = stockRepository.findById(2L).get();
		String originalStockName = expectedStock.getName();
		expectedStock.setCurrentPrice(updatedPrice);
		mockMvc.perform(put("/api/stocks/2").contentType("application/json")
				.content(new ObjectMapper().writeValueAsString(expectedStock))).andExpect(status().isOk());

		Optional<Stock> updatedStock = stockRepository.findById(2L);
		assertThat(updatedStock).isPresent().hasValueSatisfying(
				s -> Assertions.assertAll(() -> assertThat(s.getName()).isEqualTo(originalStockName),
						() -> assertThat(s.getCurrentPrice()).isEqualTo(updatedPrice)

				));
	}

}
