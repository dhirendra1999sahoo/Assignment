package com.dhirendra.exception;

/**
 * Application Sepcific Exception class
 * 
 * @author 
 *
 */
public class StockNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5673660358524295302L;

	public StockNotFoundException(String message) {
		super(message);
	}

	public StockNotFoundException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}

	public StockNotFoundException(long id) {
		
	}

}
