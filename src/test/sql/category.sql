DESC category;

SELECT name, COUNT(name) AS count
FROM category
GROUP BY name
ORDER BY count DESC
;

SELECT DISTINCT name
FROM category
ORDER BY name ASC;

SELECT c.category_id, COUNT(fc.film_id) AS film_count
FROM category AS c
         LEFT OUTER JOIN film_category AS fc on c.category_id = fc.category_id
GROUP BY c.category_id
ORDER BY film_count DESC, c.category_id ASC
;
