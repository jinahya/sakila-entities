DESC language;

SELECT name, COUNT(name) AS count
FROM language
GROUP BY name;

SELECT DISTINCT name
from language;