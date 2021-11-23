package com.dhirendra.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dhirendra.model.StockDTO;
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
public class StockController {

	@Autowired
	private StockService stockService;

	/**
	 * Returns list of all Stocks
	 * 
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getAllStocks() {
		log.info("Get all the Stocks :");
		return ResponseEntity.ok().body(stockService.getAllStocks());
	}

	/**
	 * create a stock
	 * 
	 * @param stock
	 * @param ucBuilder
	 * @return
	 */

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createNewStock(@Valid @RequestBody StockDTO stock) {
		log.info("inside stock create method :");
		com.dhirendra.entity.Stock newStock = stockService.addStock(stock);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newStock.getId())
				.toUri();
		return ResponseEntity.created(uri).body(newStock);

	}

	/**
	 * get one stock from the list
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getStock(@PathVariable("id") Long id) {
		log.info("Fetching Stock with id {}", id);
		return ResponseEntity.ok().body(stockService.getStock(id));
	}

	/**
	 * update the price of a single stock
	 * 
	 * @param id
	 * @param price
	 * @return
	 * @throws InterruptedException
	 */

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> updateStockPrice(@Valid @RequestParam Double price, @PathVariable Long id)
			throws InterruptedException {
		log.info("Updating Stock with id {}", id);
		stockService.updateStock(id, price);
		return new ResponseEntity<>(HttpStatus.OK);

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
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeStock(@PathVariable("id") Long id) throws Exception {
		log.info("Deleting Stock with id {}", id);
		stockService.removeStock(id);
	}

}
