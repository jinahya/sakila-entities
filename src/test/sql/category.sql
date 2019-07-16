DESC category;

SELECT name, COUNT(name) AS count
FROM category
GROUP BY name
ORDER BY count DESC
;

SELECT DISTINCT name
FROM category
ORDER BY name ASC;