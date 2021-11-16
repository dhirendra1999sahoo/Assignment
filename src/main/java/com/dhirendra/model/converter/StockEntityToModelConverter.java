package com.dhirendra.model.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.dhirendra.entity.Stock;
import com.dhirendra.model.StockDTO;

/**
 * This class converts Entity to Model
 * 
 * @author
 *
 */
@Component
public class StockEntityToModelConverter implements Converter<Stock, StockDTO> {

	@Override
	public StockDTO convert(Stock source) {
		StockDTO target = new StockDTO();
		target.setName(source.getName());
		target.setCurrentPrice(source.getCurrentPrice());
		target.setLastUpdate(source.getLastUpdate());
		return target;
	}

}
