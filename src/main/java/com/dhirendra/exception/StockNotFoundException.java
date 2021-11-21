package com.dhirendra.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Application Sepcific Exception class
 * 
 * @author
 *
 */

@ResponseStatus(NOT_FOUND)
public class StockNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5673660358524295302L;

	public StockNotFoundException(Long id) {
		super("Could not find Stock with id " + id + ".");
	}

}
