-- Given hour (0-23), show number of orders and total sales. [Special Query #2] 
SELECT 
    COUNT(*) AS num_orders,
    SUM(price) AS total_sales
FROM orders
WHERE EXTRACT(HOUR FROM time) = 12; 
