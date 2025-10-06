-- Given a range of hours (0-23), show the number of orders and total sales.
SELECT 
    COUNT(*) AS num_orders,
    SUM(price) AS total_sales
FROM orders
WHERE EXTRACT(HOUR FROM time) BETWEEN 11 AND 14; 
