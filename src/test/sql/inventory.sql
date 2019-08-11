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


-- ---------------------------------------------------------------------------------------------------------------------
SELECT distinct i.*
FROM inventory AS i
         LEFT JOIN rental AS r ON i.inventory_id = r.inventory_id
WHERE r.rental_id IS NULL
   OR r.return_date IS NOT NULL
ORDER BY i.inventory_id ASC
;

select i.*
from inventory AS i LEFT JOIN (SELECT * from rental where return_date IS NULL) AS r ON i.inventory_id = r.inventory_id
WHERE r.inventory_id IS NULL
-- ORDER BY i.inventory_id ASC
;

select * from rental where inventory_id = 9;

select * from inventory where inventory_id = 9;

SELECT distinct i.*
FROM inventory AS i
WHERE i.inventory_id NOT IN (SELECT inventory_id FROM rental where return_date IS NULL)
ORDER BY i.inventory_id ASC
;

SELECT i.*
FROM inventory AS i
WHERE NOT EXISTS(SELECT * FROM rental AS r WHERE i.inventory_id = r.inventory_id AND return_date IS NULL)
ORDER BY i.inventory_id ASC
;

select count(*)
from inventory;

select r.rental_id, r.return_date, i.inventory_id
from rental AS r
         LEFT JOIN inventory i on r.inventory_id = i.inventory_id
order by i.inventory_id ASC
;
