-- Given a day, show the number of orders over 10 dollars.
SELECT 
    COUNT(*) AS num_orders_over_10_dollars
FROM orders
WHERE date = '2025-04-27'
    AND price > 10;
