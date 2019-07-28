DESC inventory;

SELECT *
FROM inventory;

desc address;


SELECT f.film_id, f.title, COUNT(f.film_id) AS film_count
FROM film AS f
         INNER JOIN inventory AS i ON f.film_id = i.film_id
-- WHERE f.film_id = 480
GROUP BY f.film_id
ORDER BY film_count DESC
;


SELECT o.country, c.city, a.district, a.address, s.store_id
FROM film AS f
         INNER JOIN inventory AS i ON f.film_id = i.film_id
         INNER JOIN store AS s ON i.store_id = s.store_id
         INNER JOIN address AS a ON s.address_id = a.address_id
         INNER JOIN city AS c ON a.city_id = c.city_id
         INNER JOIN country AS o ON c.country_id = o.country_id
WHERE f.film_id = 200
ORDER BY o.country ASC, c.city ASC, a.district ASC, a.address AsC, s.store_id ASC
;

select *
from inventory
where film_id = 480;