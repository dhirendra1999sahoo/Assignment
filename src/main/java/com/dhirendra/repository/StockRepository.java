package com.dhirendra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhirendra.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

	Stock findStockByName(String name);

}
