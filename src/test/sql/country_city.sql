SELECT l.country_id, COUNT(r.city_id) AS city_count
FROM country AS l LEFT JOIN city AS r ON l.country_id = r.country_id
GROUP BY l.country_id
ORDER BY city_count DESC
;

SELECT * FROM city where country_id = 108;