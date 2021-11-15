package com.dhirendra;

import org.springframework.web.client.RestTemplate;

import com.dhirendra.model.Stock;



public class StockRestTestClient {
	
	public static final String REST_SERVICE_URI = "http://localhost:8080/assignment";
	
	
	 /* GET */
    private static void getStock(){
        System.out.println("Testing getStock API----------");
        RestTemplate restTemplate = new RestTemplate();
        Stock stock = restTemplate.getForObject(REST_SERVICE_URI+"/api/stocks/1", Stock.class);
        System.out.println(stock);
    }
    
    
    /* PUT */
    private static void updateStock() {
        System.out.println("Testing update Stock API----------");        
        RestTemplate restTemplate = new RestTemplate(); 
        Stock stock = restTemplate.getForObject(REST_SERVICE_URI+"/api/stocks/1", Stock.class);
        stock.setCurrentPrice(200d);
        restTemplate.put(REST_SERVICE_URI+"/api/stocks/1", stock);
        System.out.println(stock);
    }
    
    
    /* DELETE */
    private static void deleteStock() {
        System.out.println("Testing delete Stock API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/api/stocks/1");
    }
    
    public static void main(String args[]){
        
    	getStock();
    	updateStock();
    	deleteStock();
        
    }

}
