SELECT *
FROM project
WHERE DATEDIFF(MONTH, start_date, finish_date) = (
    SELECT MAX(DATEDIFF(MONTH, start_date, finish_date))
    FROM project
);
