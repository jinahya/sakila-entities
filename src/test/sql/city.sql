DESC city;

SELECT *
FROM city;

SELECT country_id, city
FROM city
ORDER BY country_id ASC, city ASC;

SELECT COUNT(1) AS count, city
FROM city
GROUP BY city
ORDER BY count DESC
;