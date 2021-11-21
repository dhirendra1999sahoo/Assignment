package com.dhirendra.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dhirendra.entity.Stock;
import com.dhirendra.exception.StockNotFoundException;
import com.dhirendra.model.StockDTO;
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

	public List<StockDTO> getAllStocks() {
		List<StockDTO> stocks = new ArrayList<>();
		try {
			List<Stock> listStocks = stockRepository.findAll();

			if (!CollectionUtils.isEmpty(listStocks)) {
				listStocks.stream().forEach(obj -> stocks.add(conversionService.convert(obj, StockDTO.class)));

			}
		} catch (StockNotFoundException e) {
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

	public StockDTO getStock(long id) {
		StockDTO stock = null;
		try {
			Stock singleStock = stockRepository.findById(id).orElseThrow(() -> new StockNotFoundException(id));
			if (singleStock != null) {
				stock = conversionService.convert(singleStock, StockDTO.class);
			}
		} catch (StockNotFoundException e) {
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

	@Transactional(rollbackOn = Exception.class)
	public void removeStock(long id) throws InterruptedException {
		try {
			TimeUnit.MINUTES.sleep(5);
			stockRepository.findById(id).orElseThrow(() -> new StockNotFoundException(id));
			stockRepository.deleteById(id);

		} catch (StockNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 
	 * Add new Stock
	 * 
	 * @param stock
	 * @return
	 */

	@Transactional(rollbackOn = Exception.class)
	public Stock addStock(StockDTO stockDto) {
		Stock stock;
		try {
			stock = stockRepository.save(conversionService.convert(stockDto, Stock.class));
		} catch (StockNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return stock;
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

	@Transactional(rollbackOn = Exception.class)
	public void updateStock(Long id, StockDTO currentStock) throws InterruptedException {
		try {
			TimeUnit.MINUTES.sleep(5);
			stockRepository.findById(id).orElseThrow(() -> new StockNotFoundException(id));
			Stock newStock = conversionService.convert(currentStock, Stock.class);
			newStock.setId(id);

		} catch (StockNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

	}

}
