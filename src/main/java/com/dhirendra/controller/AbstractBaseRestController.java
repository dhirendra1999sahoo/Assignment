package com.dhirendra.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * The Abstract base class to be sub-classed in all the Controllers. It defines
 * common functionalities needed in all the Controllers
 * 
 * @author
 *
 */

@Slf4j
public abstract class AbstractBaseRestController {

	protected ResponseEntity<?> createResponse(Object obj, HttpStatus status) {
		log.debug("createResponse :: creating reposnse with object and status [status={}]", status);
		return new ResponseEntity<Object>(obj, status);
	}

	protected ResponseEntity<?> createResponse(HttpStatus status) {
		log.debug("createResponse :: creating reposnse with only status={}", status);
		return new ResponseEntity<Object>(status);
	}

	protected ResponseEntity<?> createResponse(HttpHeaders httpheader, HttpStatus status) {
		log.debug("createResponse :: creating reposnse with httpheader and status[httpheader={}, status={}", httpheader,
				status);
		return new ResponseEntity<Object>(httpheader, status);
	}

}
