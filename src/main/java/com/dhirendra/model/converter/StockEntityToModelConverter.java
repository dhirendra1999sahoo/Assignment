package com.dhirendra.model.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.dhirendra.entity.Stock;

@Component
public class StockEntityToModelConverter implements Converter<Stock, com.dhirendra.model.Stock> {

	@Override
	public com.dhirendra.model.Stock convert(Stock source) {
		com.dhirendra.model.Stock target = new com.dhirendra.model.Stock();
		target.setName(source.getName());
		target.setCurrentPrice(source.getCurrentPrice());
		target.setLastUpdate(source.getLastUpdate());
		return target;
	}

}
