-- See the number of sales of a specific drink in a specific week.
SELECT 
    COUNT(*) AS times_ordered
FROM orders
WHERE week = 20 AND drinks LIKE '%Matcha Milk Tea%';