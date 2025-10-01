--Peak Sales Day: sum of top 10 order totals for a given date
SELECT date, SUM(price) AS top_10_total
FROM (
    SELECT date, price
    FROM orders
    WHERE date = '2025-08-30'
    ORDER BY price DESC
    LIMIT 10
) sub
GROUP BY date;
