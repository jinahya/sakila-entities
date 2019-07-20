DESC country;

SELECT *
FROM country;

SELECT country_id
FROM country
ORDER BY country_id;

SELECT COUNT(country)
FROM country;

SELECT country
FROM country;

SELECT COUNT(DISTINCT country)
FROM country;

SELECT DISTINCT country
FROM country;

SELECT country
FROM country
ORDER BY country ASC;

SELECT country_id, country
FROM country
ORDER BY country_id ASC
;

SELECT l.country_id, COUNT(r.city_id) AS city_count
FROM country AS l
         LEFT JOIN city AS r ON l.country_id = r.country_id
GROUP BY l.country_id
ORDER BY city_count DESC
;