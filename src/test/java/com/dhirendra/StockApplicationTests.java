package com.dhirendra;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class StockApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetAllStocks() throws Exception {
		mockMvc.perform(get("/api/stocks")).andExpect(status().isOk())
				.andExpect(content().json("["
						+ "{\"name\":\"TCS\",\"currentPrice\":3000.0,\"lastUpdate\":\"2020-12-31T22:59:59.000+00:00\"},"
						+ "{\"name\":\"HCL\",\"currentPrice\":1000.0,\"lastUpdate\":\"2012-12-31T20:59:59.000+00:00\"}"
						+ "]", true));
	}

	@Test
	void testGetStockById() throws Exception {
		mockMvc.perform(get("/api/stocks/1")).andExpect(status().isOk()).andExpect(content().json(
				"{\"name\":\"TCS\",\"currentPrice\":3000.0,\"lastUpdate\":\"2020-12-31T22:59:59.000+00:00\"}", true));
	}

}
