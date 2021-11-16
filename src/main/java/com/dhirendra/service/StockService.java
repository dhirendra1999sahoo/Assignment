package com.dhirendra.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dhirendra.entity.Stock;
import com.dhirendra.exception.StockException;
import com.dhirendra.exception.StockNotFoundException;
import com.dhirendra.repository.StockRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Performing operations on Stocks
 * 
 * 
 * @author
 *
 */
@Slf4j
@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ConversionService conversionService;

	/**
	 * Retrieve all stocks
	 * 
	 * @return
	 */
	@Transactional
	public List<com.dhirendra.model.Stock> getAllStocks() {
		List<com.dhirendra.model.Stock> stocks = new ArrayList<>();
		try {
			List<Stock> listStocks = stockRepository.findAll();

			if (!CollectionUtils.isEmpty(listStocks)) {
				listStocks.stream()
						.forEach(obj -> stocks.add(conversionService.convert(obj, com.dhirendra.model.Stock.class)));

			}
		} catch (StockException e) {
			log.error("getAllStocks :: There was an StockException exception in the Stock service");
			throw e;
		} catch (Exception e) {
			log.error("getAllStocks :: There was an unknown exception");
			throw e;
		}
		return stocks;
	}

	/**
	 * Retrieve only single stock by Id
	 * 
	 * @param id
	 * @return
	 */

	@Transactional
	public com.dhirendra.model.Stock getStock(long id) {
		com.dhirendra.model.Stock stock = null;
		try {
			Optional<Stock> singleStock = stockRepository.findById(id);
			if (singleStock.isPresent()) {
				stock = conversionService.convert(singleStock.get(), com.dhirendra.model.Stock.class);
			}
		} catch (StockException e) {
			log.error("getStock :: There was an StockException exception in the Stock service");
			throw e;
		} catch (Exception e) {
			log.error("getStock :: There was an unknown exception");
			throw e;
		}
		return stock;

	}

	/**
	 * delete Stock
	 * 
	 * @param id
	 * @throws InterruptedException
	 */

	@Transactional
	public void removeStock(long id) throws InterruptedException {
		try {
			TimeUnit.MINUTES.sleep(5);
			stockRepository.findById(id).orElseThrow(() -> new StockNotFoundException(id));
			stockRepository.deleteById(id);

		} catch (StockException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 
	 * Add new Stock
	 * @param stock
	 * @return
	 */

	@Transactional
	public Stock addStock(com.dhirendra.model.Stock stock) {
		return stockRepository.save(conversionService.convert(stock, Stock.class));
	}

	/**
	 * 
	 * Update Stock price
	 * 
	 * @param id
	 * @param currentStock
	 * @return
	 * @throws InterruptedException
	 */

	@Transactional
	public com.dhirendra.model.Stock updateStock(Long id, com.dhirendra.model.Stock currentStock)
			throws InterruptedException {
		Stock stock;
		try {
			TimeUnit.MINUTES.sleep(5);
			stockRepository.findById(id).orElseThrow(() -> new StockNotFoundException(id));
			Stock newStock = conversionService.convert(currentStock, Stock.class);
			newStock.setId(id);
			stock = stockRepository.save(newStock);

		} catch (StockException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

		return conversionService.convert(stock, com.dhirendra.model.Stock.class);

	}
	
}
