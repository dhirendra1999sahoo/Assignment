package com.dhirendra.model.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.dhirendra.model.Stock;

@Component
public class StockModelToEntityConverter implements Converter<Stock, com.dhirendra.entity.Stock> {

	@Override
	public com.dhirendra.entity.Stock convert(Stock source) {
		com.dhirendra.entity.Stock target = new com.dhirendra.entity.Stock();
		target.setName(source.getName());
		target.setCurrentPrice(source.getCurrentPrice());
		target.setLastUpdate(source.getLastUpdate());
		return target;
	}

}
