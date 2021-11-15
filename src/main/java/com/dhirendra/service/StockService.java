package com.dhirendra.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dhirendra.entity.Stock;
import com.dhirendra.exception.StockException;
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

	@Transactional
	public Stock addStock(com.dhirendra.model.Stock stock) {
		return stockRepository.save(conversionService.convert(stock, Stock.class));

	}

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

	@Transactional
	public void removeStock(long id) {
		try {

			stockRepository.deleteById(id);

		} catch (StockException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

	}

	public boolean isStockExist(com.dhirendra.model.Stock stock) {
		return stockRepository.findStockByName(stock.getName()) != null;
	}

}
