-- See sales grouped by week
SELECT 
    week, SUM(price)
FROM orders
GROUP BY week
ORDER BY week;