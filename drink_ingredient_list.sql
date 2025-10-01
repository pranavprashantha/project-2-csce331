SELECT 
    name, COUNT(ingredient_id)
FROM drinks
GROUP BY name
ORDER BY COUNT(ingredient_id) DESC;