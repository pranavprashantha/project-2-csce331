SELECT 
    COUNT(*) AS times_ordered
FROM orders
WHERE week = 20 AND drinks LIKE '%Matcha Milk Tea%';