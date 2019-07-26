DESC language;

SELECT name, COUNT(name) AS count
FROM language
GROUP BY name
ORDER BY count DESC;

SELECT DISTINCT name
FROM language
ORDER BY name ASC;