Endpoints :

GET /api/stocks (get a list of stocks)

POST /api/stocks (create a stock)

GET /api/stocks/1 (get one stock from the list)

PUT /api/stocks/1 (update the price of a single stock)

DELETE/api/stocks/1 (delete a single stock)

initial list of stocks should be created in-memory on application start-up. 

Added OpenAPI directly in the @SpringBootApplication. But there should be a separate configuration class created for this

Can do the test of endpoints at :  http://localhost:8080/assignment/swagger-ui/index.html
