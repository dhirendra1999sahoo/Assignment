package com.dhirendra.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "name", "currentPrice","lastUpdate"})
public class StockDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1567173716566795411L;
	private String name;
	private Double currentPrice;
	private Timestamp lastUpdate;

}
