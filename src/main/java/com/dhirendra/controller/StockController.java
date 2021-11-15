package com.dhirendra.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.dhirendra.model.Stock;
import com.dhirendra.service.StockService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/stocks")
public class StockController extends AbstractBaseRestController {

	@Autowired
	private StockService stockService;

	/**
	 * Returns list of all Stocks
	 * 
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStocks() {
		log.info("Get all the Stocks :");
		List<Stock> stocks = stockService.getAllStocks();
		if (stocks.isEmpty()) {
			return createResponse(HttpStatus.NOT_FOUND);
		}
		return createResponse(stocks, HttpStatus.OK);
	}

	/**
	 * 
	 * @param stock
	 * @param ucBuilder
	 * @return
	 */

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addStock(@Valid @RequestBody Stock stock, UriComponentsBuilder ucBuilder) {
		log.info("inside stock adding method :");
		if (stockService.isStockExist(stock)) {
			log.error("Unable to create. A Stock with name {} already exist", stock.getName());
			return createResponse(stock, HttpStatus.CONFLICT);
		}

		com.dhirendra.entity.Stock newStock = stockService.addStock(stock);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/stocks/{id}").buildAndExpand(newStock.getId()).toUri());
		return createResponse(headers, HttpStatus.CREATED);

	}

	/**
	 * 
	 * @param id
	 * @return
	 */

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStock(@PathVariable("id") long id) {
		log.info("Fetching Stock with id {}", id);
		Stock stock = stockService.getStock(id);

		if (stock == null) {
			log.error("Stock with id {} not found.", id);
			return createResponse(stock, HttpStatus.NOT_FOUND);
		}
		return createResponse(stock, HttpStatus.OK);

	}

	/**
	 * 
	 * @param id
	 * @param price
	 * @return
	 */

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStockPrice(@PathVariable("id") long id, Double price) {
		log.info("Updating Stock with id {}", id);

		Stock currentStock = stockService.getStock(id);

		if (currentStock == null) {
			log.error("Unable to update. Stock with id {} not found.", id);
			return createResponse(currentStock, HttpStatus.NOT_FOUND);
		}

		currentStock.setCurrentPrice(price);
		return createResponse(currentStock, HttpStatus.OK);

	}

	/**
	 * 
	 * @param id
	 * @return
	 */

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeStock(@PathVariable("id") long id) {
		log.info("Fetching & Deleting Stock with id {}", id);
		Stock stock = stockService.getStock(id);
		if (stock == null) {
			log.error("Unable to delete. Stock with id {} not found.", id);
			return createResponse(stock, HttpStatus.NOT_FOUND);
		}
		stockService.removeStock(id);
		return createResponse(HttpStatus.OK);

	}

}
