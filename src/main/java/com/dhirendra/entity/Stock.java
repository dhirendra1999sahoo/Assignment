package com.dhirendra.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Stock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 286850869828709792L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min=2, max=10)
	private String name;
	@NotNull
	@Min(1)
	private Double currentPrice;
	private Timestamp lastUpdate;

}
