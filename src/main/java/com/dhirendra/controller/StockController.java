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

/**
 * Contains all the APIs for different operations
 * 
 * 
 * @author
 *
 */
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
	 * create a stock
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
	 * get one stock from the list
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
	 * update the price of a single stock
	 * 
	 * @param id
	 * @param price
	 * @return
	 * @throws InterruptedException
	 */

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateStockPrice(@PathVariable("id") long id, Stock stock) throws InterruptedException {
		log.info("Updating Stock with id {}", id);
		Stock updatedStock;
		try {
			updatedStock = stockService.updateStockPrice(id, stock);
		} catch (InterruptedException e) {
			throw e;
		}
		return createResponse(updatedStock, HttpStatus.OK);

	}

	/**
	 * 
	 * delete a single stock
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeStock(@PathVariable("id") long id) throws Exception {
		log.info("Deleting Stock with id {}", id);
		stockService.removeStock(id);
		return createResponse(HttpStatus.OK);
	}

}
