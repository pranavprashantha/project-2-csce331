-- Get all orders made before January 1, 2025
SELECT *
FROM orders
WHERE order_date < '2025-01-01';