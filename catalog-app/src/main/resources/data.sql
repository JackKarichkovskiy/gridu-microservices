DROP TABLE IF EXISTS products;
CREATE TABLE products AS SELECT * FROM CSVREAD('/Users/ykarychkovskyi/dev/GridU Microservices Course/products.csv');