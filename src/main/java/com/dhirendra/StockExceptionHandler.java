package com.dhirendra;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dhirendra.exception.StockNotFoundException;

/**
 * Exception handler class All Exceptions are handled in this class. All
 * exceptions fall back here
 * 
 * @author
 *
 */
@ControllerAdvice
public class StockExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.debug("handleHttpRequestMethodNotSupported :: There was an exception", ex);

		final StringBuilder message = new StringBuilder();
		message.append(ex.getMethod());
		message.append(" HTTP Request method is not supported for this request. Supported methods are:");
		ex.getSupportedHttpMethods().forEach(supportedMethod -> message.append(supportedMethod + " "));

		return new ResponseEntity<Object>(new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * Handle All generic exception thrown by the application
	 *
	 * @param exception - Generic Exception thrown
	 * @param request   - the WebRequest
	 * @return {@link ResponseEntity} - the ResResponseEntity
	 */
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAllException(Exception exception) {
		logger.warn("handleAllException :: There was an unknow exception", exception);
		logger.debug("handleAllException :: There was an unknow exception");
		return new ResponseEntity<Object>(new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle All InterruptedException thrown by the application
	 *
	 * @param interruptedException - the application specific exception
	 * @param request              - the WebRequest
	 * @return {@link ResponseEntity} - the ResResponseEntity
	 */
	@ExceptionHandler({ InterruptedException.class })
	public ResponseEntity<?> handleRefException(final InterruptedException interruptedException) {
		logger.debug("handleRefException :: there was an InterruptedException", interruptedException);

		return new ResponseEntity<>(interruptedException.getMessage(), HttpStatus.NO_CONTENT);

	}

	@ExceptionHandler({ StockNotFoundException.class })
	public ResponseEntity<?> handleRefException(final StockNotFoundException stockNotFoundException) {
		logger.debug("handleRefException :: there was an StockNotFoundException", stockNotFoundException);

		return new ResponseEntity<>(stockNotFoundException.getMessage(), HttpStatus.NOT_FOUND);

	}
}
