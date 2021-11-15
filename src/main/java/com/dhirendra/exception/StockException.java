package com.dhirendra.exception;

public class StockException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5673660358524295302L;

	public StockException(String message) {
		super(message);
	}

	public StockException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}

}
