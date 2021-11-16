package com.dhirendra.model.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.dhirendra.entity.Stock;
import com.dhirendra.model.StockDTO;

@Component
public class StockModelToEntityConverter implements Converter<StockDTO, Stock> {

	@Override
	public Stock convert(StockDTO source) {
		com.dhirendra.entity.Stock target = new com.dhirendra.entity.Stock();
		target.setName(source.getName());
		target.setCurrentPrice(source.getCurrentPrice());
		target.setLastUpdate(source.getLastUpdate());
		return target;
	}

}
