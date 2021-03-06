DESC address;

SELECT c.city_id, COUNT(*) AS address_count
FROM address AS a
         JOIN city AS c ON a.city_id = c.city_id
GROUP By a.city_id
ORDER BY c.city_id ASC
;

SELECT c.city_id, COUNT(*) AS address_count
FROM city AS c
         LEFT OUTER JOIN address AS a ON c.city_id = a.city_id
GROUP BY c.city_id
ORDER BY address_count DESC
;

SELECT city_id, district, address
FROM address
ORDER BY city_id ASC, district ASC
;

SELECT city_id, COUNT(district) AS district_count
FROM address
GROUP BY city_id
ORDER BY district_count DESC
;

SELECT city_id, COUNT(1) AS address_count
FROM address
GROUP BY city_id
ORDER BY address_count DESC
;

SELECT DISTINCT a.district AS district, c.city AS city, t.country AS country
FROM address AS a
         INNER JOIN city AS c ON a.city_id = c.city_id
         INNER JOIN country AS t ON c.country_id = t.country_id
ORDER BY country, city, district
;

SELECT DISTINCT t.country AS country, c.city AS city, a.district AS district
FROM country AS t
         LEFT JOIN city AS c ON c.country_id = t.country_id
         LEFT JOIN address AS a ON a.city_id = c.city_id
ORDER BY country, city, district
;


-- ------------------------------------------------------------------------------------------------------ print location
SELECT s.txt                                                             AS 'TXT',
       s.bin                                                             AS 'BIN',
       CONCAT(s.o, ' -> ', (CASE s.o WHEN '00' THEN 'BE' ELSE 'LE' END)) AS '(1) ORDER',
       CONV(HEX(REVERSE(UNHEX(s.t))), 16, 10)                            AS '(4) TYPE',
       s.x                                                               AS '(8) x',
       s.y                                                               AS '(8) y',
       s.h
FROM (SELECT ST_AsText(location)                     AS txt,
             ST_AsWKB(location)                      AS bin,
             SUBSTRING(HEX(location) FROM 9 FOR 2)   AS o, -- byte order
             SUBSTRING(HEX(location) FROM 11 FOR 8)  AS t, -- geometry type
             SUBSTRING(HEX(location) FROM 19 FOR 16) AS x, -- x
             SUBSTRING(HEX(location) FROM 35 FOR 16) AS y, -- y
             HEX(location)                           AS h
      FROM address
      ORDER BY address_id ASC) AS s
;

