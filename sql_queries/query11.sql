--10 cheapest items in inventory
SELECT name, unit_price
FROM inventory
ORDER BY unit_price ASC
LIMIT 10;
