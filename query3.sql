SELECT
  "date"                AS day,
  SUM(price)            AS total_sales,
  COUNT(*)              AS orders_count
FROM orders
GROUP BY "date"
ORDER BY total_sales DESC, orders_count DESC
LIMIT 10;
