-- Select top 10 sums of order total grouped by day in descending order by order total[Special Query #4]
SELECT 
    name, COUNT(ingredient_id)
FROM drinks
GROUP BY name
ORDER BY COUNT(ingredient_id) DESC;